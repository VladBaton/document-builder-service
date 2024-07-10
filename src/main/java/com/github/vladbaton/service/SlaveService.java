package com.github.vladbaton.service;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.exception.*;
import com.github.vladbaton.repository.DocRepository;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.dto.UserDTO;
import com.github.vladbaton.resource.dto.builder.UserDTOBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.vladbaton.service.DocService.getFilename;

@ApplicationScoped
public class SlaveService {
    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Transactional(rollbackOn = Exception.class)
    public void searchForSlaves(MultipartFormDataInput inputForm) {
        Map<String, List<InputPart>> uploadForm = inputForm.getFormDataMap();
        if (uploadForm.get("slaves") == null) {
            throw new BadFileException("slaves not found");
        }
        for (InputPart inputPart : uploadForm.get("slaves")) {
            String filename = getFilename(inputPart.getHeaders());
            if(filename == null || !filename.endsWith(".xlsx"))
                throw new BadFileException("Неверное расширение файла", filename);
            try (XSSFWorkbook workbook = readWorkbook(inputPart)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                try {
                    if (sheet.getRow(0).getCell(0).getRichStringCellValue() == null) {
                        throw new BadFileException("В первой строке файла не обнаружена шапка таблицы", filename);
                    }
                } catch (NullPointerException ex) {
                    throw new BadFileException("Первая строка файла не содержит шапку", filename);
                }
                List<UserDTO> slavesToRegister = new ArrayList<>();
                for(int i = 1, iend = sheet.getLastRowNum(); i <= iend; i++) {
                    UserDTO userDTO = buildUserDTO(sheet.getRow(i), filename);
                    slavesToRegister.add(userDTO);
                }
                userService.registerUsers(slavesToRegister);
            } catch (NumberFormatException ex) {
                throw new BadRequest("Неверный формат чисел в файле");
            } catch (org.hibernate.exception.ConstraintViolationException ex) {
                throw ex;
            } catch (ConstraintViolationException exception) {
                throw new BadFileException(
                        "Значения полей в файле нарушают правила валидации: " +
                                exception.getConstraintViolations()
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .toList(),
                        filename
                );
            } catch (Exception ex)
            {
                throw new FailedToUploadFileException(ex.getMessage(), filename);
            }
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateSlave(String username, UserDTO user) {
        userRepository.findByUsername(username).ifPresentOrElse(
                (foundUser) -> {
                    foundUser.setUsername(user.getUsername());
                    foundUser.setPassword(user.getPassword());
                    foundUser.setRole("User");
                    foundUser.setEmail(user.getEmail());

                    userRepository.persistAndFlush(foundUser);
                },
                () -> {
                    throw new UserNotFoundByUsernameException(username);
                }
        );
    }

    public List<User> getSlaves(String masterName) {
        User master = userRepository
                .findByUsername(masterName)
                .orElseThrow(() -> new UserNotFoundByUsernameException(masterName));
        return userRepository.find("director", master.getUserId()).list();
    }

    private XSSFWorkbook readWorkbook(InputPart inputPart) throws FailedToUploadFileException {
        try {
            InputStream inputStream = inputPart.getBody(InputStream.class, null);
            return new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            throw new FailedToUploadFileException();
        }
    }

    private UserDTO buildUserDTO(Row row, String filename)
            throws BadFileException, NullPointerException, NumberFormatException {
        String masterEmail = row.getCell(5).getStringCellValue();
        User foundMaster = userRepository.findByEmail(masterEmail).orElseThrow();
        String email = row.getCell(4).getStringCellValue();
        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]+")) {
            throw new BadFileException("email в файле имеют неправильный формат", filename);
        }
        return new UserDTOBuilder()
                .setSurname(row.getCell(0).getStringCellValue())
                .setName(row.getCell(1).getStringCellValue())
                .setPatronymic(row.getCell(2).getStringCellValue())
                .setPhone(Long.parseLong( String.format("%.0f", row.getCell(3).getNumericCellValue())))
                .setEmail(email)
                .setDirector(foundMaster.getUserId())
                .setUsername(email.split("@")[0])
                .setPassword("123abcABC")
                .setRole("Slave")
                .build();
    }
}
