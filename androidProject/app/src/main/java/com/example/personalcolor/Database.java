package com.example.personalcolor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Database {
    public Connection con = null;
    private static final String url = "jdbc:mysql://110.46.46.108:3306/project";
    private static final String id = "test";
    private static final String password = "1234";

    // DB 적재
    public Database(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // DB 연결
    private void DB_Connect() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(url, id, password);
        }
    }

    // ID 찾기
    public ResultSet findID(String name) throws SQLException {
        String query = "SELECT * FROM Member WHERE ID = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            DB_Connect();
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rs;
    }

    //새로운 계정 데이터베이스에 입력
    public void insertUser(String[] userInformation) throws SQLException {
        String query = "INSERT INTO Member (ID, PW, Name1, Gender, Address) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            DB_Connect();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userInformation[0]);
            pstmt.setString(2, userInformation[1]);
            pstmt.setString(3, userInformation[2]);
            pstmt.setString(4, userInformation[3]);
            pstmt.setString(5, userInformation[4]);
            int insertedRows = pstmt.executeUpdate();

            if (insertedRows > 0) {
                System.out.println("데이터 삽입 성공");
            } else {
                System.out.println("데이터 삽입 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 이미지 찾는 코드
    public ResultSet findImage(String ClothesID) throws SQLException {
        String query = "SELECT * FROM ClothesInfo WHERE ClothesID = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            DB_Connect();
            stmt = con.prepareStatement(query);
            stmt.setString(1, ClothesID);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rs;
    }

    // 색깔 정보 가져오는 코드
    public ResultSet getColorInfo(String personalColor) throws SQLException {
        String query = "SELECT ColorHex, ColorName FROM ColorInfo WHERE PersonalColor = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            DB_Connect();
            stmt = con.prepareStatement(query);
            stmt.setString(1, personalColor);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rs;
    }
}
