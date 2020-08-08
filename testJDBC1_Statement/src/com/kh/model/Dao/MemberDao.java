package com.kh.model.Dao;
// crud : create read update delete

import com.kh.model.vo.Member;

import java.sql.*;
import java.util.ArrayList;

// DAO (Data Access Object)
// 1) DB에 접속(연결)하여
// 2) Controller 에서 전달받은 데이터를 sql 구문을 통해서 DB에 전송 및 결과 받기
// 3) SELECT 구문을 제외한 DML(insert/update/delete)의 경우 트랜잭션 처리하기
public class MemberDao {
    /*
     * 본격적으로 JDBC
     *
     * JDBC에서 사용되는 각각의 객체 역할`
     * - Connection : DB 의 연결 정보를 담는 객체 (JDBC 드라이버와 DB 사이를 연결해주는 일종의 길)
     * - [Prepared]Statement : Connection 객체를 통해 DB에 SQL 문을 전송하고 그에 해당하는 결과를 받는 객체
     * - ResultSet : Select 문을 사용한 질의 성공 시 반환되는 객체
     *               (SQL 질의에 의해 생성된 테이블(SELECT 한 결과)를 담고 있으며, 커서(Cursor)를 가지고
     *                특정 행에 대한 참조를 조작한다.)
     *
     * * 처리 순서
     * 1. jdbc driver 등록처리 : 해당 DataBase 벤더사가 제공하고 있는 클래스로 등록
     * 2. Connection 객체 생성 : 등록된 클래스를 이용하여 DB에 연결하여 생성
     * 3. Statement 객체 생성 : Connection 객체를 이용하여 쿼리문을 실행할 객체를 생성
     * 4. 쿼리문 전송 및 실행 결과 받기 : Statement 객체를 이용하여 실행 및 결과 받기
     * 5. 실행 결과 : Select 경우 >> ResultSet(조회된 데이터들이 담겨있다.)
     *              Insert,Update,Delete 경우 >> int(처리된 행의 갯수가 담겨있다.)
     * 6_1 ) 실행 결과가 ResultSet일 경우 ResultSet에 있는 데이터들 가지고 객체 생성
     * 6_2 ) 실행 결과가 int값일 경우 트랜잭션 처리(commit, rollback)
     *
     * 7) 위에 생성했던 것을 반드시 자원 반납 (
     *
     * */
    public ArrayList<Member> selectAll() {
        // 처리된 결과의 결과(조회된 회원들)을 받아줄 ArrayList 선언
        ArrayList<Member> list = new ArrayList<>();
        // DB의 연결정보를 담는 객체선언
        Connection conn = null;
        // SQL 문을 전송하고 그에 해당하는 결과를 받는 객체를 선언
        Statement stmt = null;
        // 쿼리 실행 후 조회된 결과 값들이 실질적으로 담길 ResultSet 객체 선언
        ResultSet rset = null;

        String sql = "SELECT * FROM MEMBER";
        try {
            // 1) jdbc driver 등록처리
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 2) Connection 객체 생성
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            // 3) Statement 객체 생성
            stmt = conn.createStatement();
            // 4)5) 쿼리문 전송, 실행 결과 받기
            rset = stmt.executeQuery(sql);
            // executeUpdate() --> insert / delete / update 일 경우 실행 메소드
            // executeQuery() --> select 일 경우 실행 메소드
            // 6)
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
                stmt.close();
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
        Statement stmt = null;
        ResultSet rset = null;
        String sql2 = "SELECT * FROM MEMBER WHERE USERID=" + "'" + id + "'";

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql2);
            while (rset.next()) {


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

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // 7) 자원반납
                rset.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return m;
    }

    public int insertMember(Member m) {
        // 처리된 결과를 받아줄 변수 선언(처리된 행의 갯수)
        int result = 0;
        // DB의 연결정보를 담는 객체 선언
        Connection conn = null;
        // SQL문을 전송하고 그에 해당하는 결과를 받는 객체 선언
        Statement stmt = null;


        // 실행할 쿼리문(완성형태) --> 끝에 세미콜론은 작성X !!!
        String sql = "INSERT INTO MEMBER VALUES("
                + "'" + m.getUserId() + "'"
                + ",'" + m.getPassword() + "'"
                + ",'" + m.getUserName() + "'"
                + ",'" + m.getGender() + "'"
                + ",'" + m.getAge() + "'"
                + ",'" + m.getEmail() + "'"
                + ",'" + m.getPhone() + "'"
                + ",'" + m.getAddress() + "'"
                + ",'" + m.getHobby() + "'"
                + ", SYSDATE)";
        System.out.println(sql);
        try {
            // 1) jdbc drive 등록처리
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //해당 디렉토리를 찾지 못하면 ClassNotFoundException 발생
            // 2) Connection 객체 생성 : DB연결에 필요한 url,id,pwd
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "JDBC", "JDBC");
            /*
             * jdbc:oracle:thin --> JDBC 드라이버가 thin 타입임을 의미한다.
             * @127.0.0.1       --> 오라클이 설치된 서버의 ip가 자신의 컴퓨터(local)인 경우 @localhost 대체가능
             * XE               --> 접속할 오라클 SID
             * id               --> 사용자 아이디
             * pwd              --> 비밀번호
             * */
            // 3) Statement 객체 생성 : 쿼리문 실행하기 위해
            stmt = conn.createStatement();
            // 4) 5) 쿼리문 전송, 실행 결과 받기
            result = stmt.executeUpdate(sql); //insert update delete 는 executeUpdate 문을 사용
            System.out.println("result");

            // 6_2) 트랜잭션 처리(insert update delete 작업 후에는 반드시 트랜잭션 처리)
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
            // 7) 자원 반납
            try {
                // 실행한 객체가 늦은 순부터 종료
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public ArrayList<Member> selectByName(String name) {
        ArrayList<Member> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rSet = null;

        String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" + name + "%'";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            stmt = conn.createStatement();
            rSet = stmt.executeQuery(sql);

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

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rSet.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    public int deleteMember(String id) {
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        String sql = "DELETE FROM MEMBER WHERE USERID = '" + id + "'";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
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

    public int updateMember(Member m) {
        int result=0;
        Connection conn = null;
        Statement stmt = null;
        String sql = "UPDATE MEMBER SET(PASSWORD, EMAIL, PHONE, ADDRESS)=('"
                + m.getPassword() + "','" + m.getEmail() + "','" + m.getPhone() + "','" + m.getAddress() + "')"
                + "WHERE USERID='"+m.getUserId()+"'";
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
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
