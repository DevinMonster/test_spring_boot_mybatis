package com.devin.utils;

import com.devin.entity.User;
import com.devin.entity.request.UserRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.UUID;

public class Utils {

    public static void main(String[] args) {
        System.out.println(generateUUName("aaa.jpg"));
    }

    public static String generateUUName(String fileName) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date()) + "-" + UUID.randomUUID().toString() + "." + getExtension(fileName);
    }

    public static String getExtension(String fileName) {
        if (!checkLeagle(fileName)) throw new InputMismatchException("文件名: " + fileName + "不合法");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private static boolean checkLeagle(String fileName) {
        assert fileName != null;
        fileName = fileName.trim();
        int dot = fileName.lastIndexOf(".");
        return dot != -1 && dot != 0 && dot + 1 != fileName.length();
    }

    public static boolean userCheck(User user, UserRequest request) {
        return  user != null &&
                request.getUsername().equals(user.getUsername()) &&
                Objects.equals(MD5Utils.encode(request.getPassword()), user.getPassword());
    }

    public static boolean userLeagle(UserRequest user) {
        return user != null && checkUserName(user.getUsername()) && checkPassword(user.getPassword(), 6, 30);
    }

    private static boolean checkPassword(String password, int minlen, int maxlen) {
        if (password == null) return false;
        int len = password.length();
        if (len < minlen && len > maxlen) return false;
        // check without blank character
        for (int i = 0; i < len; i++) {
            if (' ' == password.charAt(i)) return false;
        }
        return true;
    }

    private static boolean checkUserName(String name) {
        if (name == null) return false;
        int len = name.length();
        for (int i = 0; i < len; i++) {
            if (' ' == name.charAt(i)) return false;
        }
        return true;
    }

    public static boolean retBool(Integer insert) {
        return null != insert && insert >= 1;
    }
}
