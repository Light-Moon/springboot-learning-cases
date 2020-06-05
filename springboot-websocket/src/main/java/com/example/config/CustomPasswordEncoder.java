package com.example.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 参考：https://blog.csdn.net/Hello_World_QWP/article/details/81811462
 * 此类是为解决报错：There is no PasswordEncoder mapped for the id "null"
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
