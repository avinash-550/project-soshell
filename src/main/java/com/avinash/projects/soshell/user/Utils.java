package com.avinash.projects.soshell.user;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {
    public static String getPasswordHash(String password){
        return DigestUtils.sha256Hex(password);
    }
}
