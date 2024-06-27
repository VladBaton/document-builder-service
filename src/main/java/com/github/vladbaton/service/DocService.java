package com.github.vladbaton.service;

import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.exception.*;
import com.github.vladbaton.exception.FileNotFoundException;
import com.github.vladbaton.repository.DocRepository;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.pojo.BuildDocumentRequestEntry;
import com.github.vladbaton.resource.pojo.ReplacementEntry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MultivaluedMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@ApplicationScoped
public class DocService {
    @ConfigProperty(name = "upload.directory")
    String uploadDirectory;

    @Inject
    DocRepository docRepository;

    @Inject
    UserRepository userRepository;

    @Transactional
    public void uploadDoc(MultipartFormDataInput input, String username)
            throws FailedToUploadFileException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        }
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        Doc doc = null;
        if (uploadForm.get("file") == null) {
            throw new FailedToUploadFileException();
        }
        for (InputPart inputPart : uploadForm.get("file")) {
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            String filename = getFilename(header);
            Doc foundDoc = docRepository.findByUserAndFilename(user, filename);
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                writeFile(inputStream, filename, username);
            } catch (Exception e) {
                throw new FailedToUploadFileException(filename, e.getMessage());
            }
            if (foundDoc == null) {
                doc = new Doc();
                doc.setUser(user);
                doc.setFileReference(new File(uploadDirectory + File.separator + username).getAbsolutePath() + File.separator + filename);
                user.getDocs().add(doc);
                docRepository.persistAndFlush(doc);
                userRepository.persistAndFlush(user);
            } else {
                foundDoc.setLastUpdatedDate(new Date());
            }
        }
    }

    public Doc getDocByUsernameAndFilename(String username, String filename)
            throws UserNotFoundByUsernameException, FileNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        }
        Doc doc = docRepository.findByUserAndFilename(user, filename);
        if (doc == null) {
            throw new FileNotFoundException(username, filename);
        }
        return doc;
    }

    public Set<Doc> getListOfDocs(String username) throws UserNotFoundByUsernameException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        }
        return user.getDocs();
    }

    public InputStream downloadDoc(String username, String filename)
            throws UserNotFoundByUsernameException, FileNotFoundException {
        User user = findUserByUsername(username);
        Doc foundDoc = findDocByUserAndFilename(user, filename);
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(foundDoc.getFileReference());
        } catch (IOException ex) {
            throw new FailedToDownloadFileException(filename);
        }
        return fileStream;
    }

    public ByteArrayOutputStream buildDocument(String username, String filename, List<BuildDocumentRequestEntry> replacements) {
        User user = findUserByUsername(username);
        Doc foundDoc = findDocByUserAndFilename(user, filename);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream docxInputStream = new FileInputStream(foundDoc.getFileReference());
             XWPFDocument document = new XWPFDocument(docxInputStream);) {
            List<ReplacementEntry> listOfReplacementEntries = new ArrayList<>();
            for(BuildDocumentRequestEntry replacement: replacements) {
                for (XWPFParagraph paragraph : document.getParagraphs()) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if(text.contains(replacement.getTarget())) {
                            listOfReplacementEntries.add(
                                    new ReplacementEntry(
                                            run, replacement.getTarget(), replacement.getReplacement()
                                    ));
                        }
                    }
                }
                for(XWPFTable table: document.getTables()) {
                    for(XWPFTableRow row: table.getRows()) {
                        for(XWPFTableCell cell: row.getTableCells()) {
                            for(XWPFParagraph paragraph: cell.getParagraphs()) {
                                for(XWPFRun run: paragraph.getRuns()) {
                                    if(run.getText(0).contains(replacement.getTarget())) {
                                        listOfReplacementEntries.add(
                                                new ReplacementEntry(
                                                        run, replacement.getTarget(), replacement.getReplacement()
                                                ));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(ReplacementEntry entry: listOfReplacementEntries) {
                String newText = entry.getRun().text().replace(entry.getTarget(), entry.getReplacement());
                entry.getRun().setText(newText, 0);
            }
            document.write(outputStream);
        } catch (IOException ex) {
            throw new DocumentBuildingError(filename);
        }
        return outputStream;
    }

    private User findUserByUsername(String username) throws UserNotFoundByUsernameException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        }
        return user;
    }

    private Doc findDocByUserAndFilename(User user, String filename) {
        Doc foundDoc = docRepository.findByUserAndFilename(user, filename);
        if (foundDoc == null) {
            throw new FileNotFoundException(filename);
        }
        return foundDoc;
    }

    private String getFilename(MultivaluedMap<String, String> header) {
        for (String key : header.getFirst("Content-Disposition").split(";")) {
            if (key.trim().startsWith("filename")) {
                return key.trim().replace("filename=\"", "").replace("\"", "");
            }
        }
        return null;
    }

    private void writeFile(InputStream inputStream, String filename, String username)
            throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        String userUploadDirectory = uploadDirectory + File.separator + username;
        File file = new File(new File(userUploadDirectory).getAbsolutePath());
        file.mkdirs();
        file = new File(file.getAbsolutePath() + File.separator + filename);
        Files.write(Paths.get(file.getAbsolutePath()), bytes, StandardOpenOption.CREATE);
    }
}
