package com.github.vladbaton.help;

import com.github.vladbaton.exception.WrongAuthorizationHeaderException;

import java.util.Base64;

public class AuthChecker {
    static public String[] checkBasicAuth(String authorization)
            throws WrongAuthorizationHeaderException {
        if (authorization.isEmpty()) {
            throw new WrongAuthorizationHeaderException();
        }
        String[] loginAndPassword = new String
                (
                    Base64.getDecoder()
                    .decode(authorization.replaceAll("Basic ", "")
                    .getBytes())
                )
                .split(":");
        if (loginAndPassword.length != 2) {
            throw new WrongAuthorizationHeaderException();
        }
        return loginAndPassword;
    }

}
