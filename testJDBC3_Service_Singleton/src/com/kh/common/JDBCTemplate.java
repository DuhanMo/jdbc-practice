package com.kh.common;

import java.sql.*;

// 기존 Service 단 DB Connection 관련, 트랜잭션 관련 코드를 모두가 공유해서 사용할 수 있도록
// 히니의 객체로 만들어서 공유해서 사용하겠다.
public class JDBCTemplate {
    // Connection 객체를 생성해서 반환하는 메소드
    // 즉 DB연결을 해주는 메소드
    public static Connection getConnection(){
        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");

            conn.setAutoCommit(false); // 기본값은 true
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
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



