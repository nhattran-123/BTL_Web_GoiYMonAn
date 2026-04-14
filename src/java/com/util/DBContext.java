package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    
    private final String serverName = "localhost";
    private final String dbName = "HealthyFoodDB"; 
    private final String portNumber = "3306";
    private final String userID = "root";       
    private final String password = "dkhai"; 

    public Connection getConnection() throws Exception {
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, userID, password);
    }
    
    // Hàm test thần thánh của Lĩnh
    public static void main(String[] args) {
        try {
            DBContext db = new DBContext();
            Connection conn = db.getConnection();
            System.out.println(">>> TUYỆT VỜI! KẾT NỐI HealthDB THÀNH CÔNG! <<<");
            conn.close();
        } catch (Exception e) {
            System.out.println(">>> LỖI KẾT NỐI RỒI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}