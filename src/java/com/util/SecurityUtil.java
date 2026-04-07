package com.util;

import java.security.MessageDigest;

public class SecurityUtil {
    
    // Hàm băm mật khẩu bằng thuật toán SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes("UTF-8"));
            
            // Chuyển byte thành chuỗi Hex (chữ và số)
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString(); // Trả về mật khẩu đã băm
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi băm mật khẩu", e);
        }
    }
}