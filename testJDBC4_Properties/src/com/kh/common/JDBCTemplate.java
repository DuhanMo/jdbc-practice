package com.kh.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
    // 기존의 방시에서
    // jdbc driver로딩, DB연결을 위한 정보(url,id,password)를 직접코딩해도 사용이 가능하다.
    // --> 정적코딩
    // --> 추후 DB 자체 변경 또는 연결정보가 변경되어지는 경우 코드를 직접 수정하고 적용하기 위해서 컴파일 작업해야한다.
    // --> 유지보수 불편 --> 일반관리자(사이트 관리자)가 코드를 볼줄 모르고 자바를 모를경우를 위해서 DB연결정보를 설정파일로 빼놓고 코딩을 한다.

    // Connection 객체를 생성해서 반환하는 메소드
    // 즉 DB연결을 해주는 메소드
    public static Connection getConnection(){
        Connection conn = null;

        try {
            Properties prop = new Properties();
            prop.load(new FileReader("resources/driver.properties"));

            String driver = prop.getProperty("driver"); //oracle.jdbc.driver.OracleDriver
            String url = prop.getProperty("url"); //jdbc:oracle:thin:@localhost:1521:xe"
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,password);
            conn.setAutoCommit(false); // 기본값은 true
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


        return conn;
    }
    // 자원을 반납해주는 메소드
    // 전달받은 Connection 객체 반납해주는 메소드
    public static void close(Connection conn){
        try{
            if (conn != null && !conn.isClosed())
                // conn 이 생성되어있고, 객체가 안닫혀있는 경우 닫히도록!
                conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // PreparedStatement 는 Statement의 하위 클래스.Polymorphism 적용
    public static void close(Statement stmt){
        try {
            if(stmt != null && !stmt.isClosed()) stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void close(ResultSet rset){
        try{
            if(rset != null && !rset.isClosed()) rset.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // 트랜잭션 관련 처리
    // service 단에서 직접 conn.commit(), conn.rollback() 사용해도 동일하지만
    // 코드중복, 일일이 try~catch문을 다시설정해야한다
    public static void commit(Connection conn){
        try{
            if(conn != null && !conn.isClosed()) conn.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void rollback(Connection conn){
        try{
            if(conn != null && !conn.isClosed()) conn.rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
