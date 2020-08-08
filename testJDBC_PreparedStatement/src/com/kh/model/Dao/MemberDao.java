package com.kh.model.Dao;
// crud : create read update delete

import com.kh.model.vo.Member;
import com.sun.org.glassfish.external.statistics.annotations.Reset;

import java.sql.*;
import java.util.ArrayList;

// DAO (Data Access Object)
// 1) DB에 접속(연결)하여
// 2) Controller 에서 전달받은 데이터를 sql 구문을 통해서 DB에 전송 및 결과 받기
// 3) SELECT 구문을 제외한 DML(insert/update/delete)의 경우 트랜잭션 처리하기
@SuppressWarnings("SqlResolve")
public class MemberDao {
    /*
     *
     * PreparedStatement : Statement 만와 같은 SQL문을 전달하여 실행하고 결과를 반환받는 객체지
     *                      실행시간동안 인자값을 위한 공간을 확보할 수 있다는 점에서 Statement와 다르다
     * Statement는 Connection을 통해 객체 생성 후 완성된 SQL 을 전송했다면
     * PreparedStatement는 Connection을 통해 객체 생성 시 미완성된 SQL문을 가지고 생성
     *
     * SQL문을 정의 시 홀더(?)라는걸 이용하여 SQL문을 정의할 수 있다.
     * - 홀더(?) : sql문장에서 나타나는 토큰으로 sql구문이 실행되기전에 실제 값으로 대체되어야 한다.
     *
     *
     * */
    public int insertMember(Member m) {
        int result =0;
        Connection conn = null;
        PreparedStatement pStmt = null;

        // 실행할 쿼리문 : 미완성상태(?로 대체)로
        String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
//String sql = "insert into MEMBER "
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,m.getUserId());
            pStmt.setString(2,m.getPassword());
            pStmt.setString(3,m.getUserName());
            pStmt.setString(4,m.getGender());
            pStmt.setInt(5,m.getAge());
            pStmt.setString(6,m.getEmail());
            pStmt.setString(7,m.getPhone());
            pStmt.setString(8,m.getAddress());
            pStmt.setString(9,m.getHobby());

            result = pStmt.executeUpdate();
            if (result>0) {conn.commit();}
            else conn.rollback();




        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                pStmt.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }

    public ArrayList<Member> selectByName(String name) {
        ArrayList<Member> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rSet = null;

        // 미완성 쿼리 작성
//        String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%;"; 오류 발생
        String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","JDBC","JDBC");
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,"%"+name+"%");

            rSet = pStmt.executeQuery();
            while (rSet.next()) {
                list.add(new Member(rSet.getString("USERID"),
                        rSet.getString("PASSWORD"),
                        rSet.getString("USERNAME"),
                        rSet.getString("GENDER"),
                        rSet.getInt("AGE"),
                        rSet.getString("EMAIL"),
                        rSet.getString("PHONE"),
                        rSet.getString("ADDRESS"),
                        rSet.getString("HOBBY"),
                        rSet.getDate("ENROLLDATE"))
                );
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rSet.close();
                pStmt.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


        return list;
    }

    public ArrayList<Member> selectAll() {
        // 처리된 결과의 결과(조회된 회원들)을 받아줄 ArrayList 선언
        ArrayList<Member> list = new ArrayList<>();
        // DB의 연결정보를 담는 객체선언
        Connection conn = null;
        // SQL 문을 전송하고 그에 해당하는 결과를 받는 객체를 선언
        PreparedStatement pStmt = null;
        // 쿼리 실행 후 조회된 결과 값들이 실질적으로 담길 ResultSet 객체 선언
        ResultSet rset = null;

        String sql = "SELECT * FROM MEMBER";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            pStmt = conn.prepareStatement(sql);
            rset = pStmt.executeQuery();

            while (rset.next()) {
                // 담기 전용 객체
                Member m = new Member();
                // 현재 rset의 커서가 가리키고 있는 row의 데이터들을 컬럼명을 통해서 가져온다.
                // 컬럼명은 대소문자 구분 없음.
                m.setUserId(rset.getString("USERID"));
                m.setPassword(rset.getString("PASSWORD"));
                m.setUserName(rset.getString("USERNAME"));
                m.setAddress(rset.getString("GENDER"));
                m.setAge(rset.getInt("AGE"));
                m.setEmail(rset.getString("EMAIL"));
                m.setPhone(rset.getString("PHONE"));
                m.setAddress(rset.getString("ADDRESS"));
                m.setHobby(rset.getString("HOBBY"));
                m.setEnrollDate(rset.getDate("ENROLLDATE"));
                // 리스트에 해당 회원 한명씩 추가
                list.add(m);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // 7) 자원반납
                rset.close();
                pStmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return list;
    }

    public Member selectOne(String id) {
        Member m = new Member();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rSet = null;
        String sql = "SELECT * FROM MEMBER WHERE USERID=?";

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,id);
            rSet = pStmt.executeQuery();

            while (rSet.next()) {


                m.setUserId(rSet.getString("USERID"));
                m.setPassword(rSet.getString("PASSWORD"));
                m.setUserName(rSet.getString("USERNAME"));
                m.setAddress(rSet.getString("GENDER"));
                m.setAge(rSet.getInt("AGE"));
                m.setEmail(rSet.getString("EMAIL"));
                m.setPhone(rSet.getString("PHONE"));
                m.setAddress(rSet.getString("ADDRESS"));
                m.setHobby(rSet.getString("HOBBY"));
                m.setEnrollDate(rSet.getDate("ENROLLDATE"));

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // 7) 자원반납
                rSet.close();
                pStmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return m;
    }
    public int updateMember(Member m) {
        int result=0;
        Connection conn = null;
        PreparedStatement pStmt = null;
        String sql = "UPDATE ";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            pStmt = conn.prepareStatement(sql);
            pStmt.setString();
            result = pStmt.executeUpdate();
            if (result > 0) {
                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}




