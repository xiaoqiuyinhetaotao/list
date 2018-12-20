package com.itheima.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {

    public static String encodingPassWord(String password){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        return bc.encode(password);
    }


    /**
     * 测试加密
     * @param args
     */
    public static void main(String[] args) {
        String passWord = "123";
        String word = encodingPassWord(passWord);
        System.out.println(word);
        //$2a$10$zMg5dI0i4MFuHHAymCvMPeb5sJBhDuo0SJpwih9/7uRXCqXQUzOR.
    }
}
