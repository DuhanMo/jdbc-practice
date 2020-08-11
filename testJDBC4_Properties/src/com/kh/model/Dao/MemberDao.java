package com.kh.model.Dao;

import com.kh.model.vo.Member;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;

public class MemberDao {
    private Properties prop = new Properties();

    public MemberDao() {
        try {
            prop.load(new FileReader("resources/query.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int insertMember(Connection conn, Member m) {
        int result = 0;
        PreparedStatement pStmt = null;
//        String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
        String sql = prop.getProperty("insertMember");
        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, m.getUserId());
            pStmt.setString(2, m.getPassword());
            pStmt.setString(3, m.getUserName());
            pStmt.setString(4, m.getGender());
            pStmt.setInt(5, m.getAge());
            pStmt.setString(6, m.getEmail());
            pStmt.setString(7, m.getPhone());
            pStmt.setString(8, m.getAddress());
            pStmt.setString(9, m.getHobby());

            result = pStmt.executeUpdate();
            if (result > 0) {
                commit(conn);
            } else {
                rollback(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStmt);
        }

        return result;
    }

    public int deleteMember(Connection conn, String id) {
        int result = 0;
        PreparedStatement pStmt = null;
        String sql = prop.getProperty("deleteMember");
        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            result = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStmt);

        }
        return result;
    }

    public ArrayList<Member> selectAll(Connection conn) {
        ArrayList<Member> list = new ArrayList<>();
        PreparedStatement pStmt = null;
        ResultSet rset = null;
        String sql = prop.getProperty("selectAll");
        try {
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
                m.setDelFlag(rset.getString("DEL_FLAG"));
                // 리스트에 해당 회원 한명씩 추가
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pStmt);
        }
        return list;
    }

    public Member selectOne(Connection conn, String id) {
        Member m = new Member();
        PreparedStatement pStmt = null;
        ResultSet rSet = null;
        String sql = prop.getProperty("selectOne");
        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rSet);
            close(pStmt);
        }
        return m;
    }

    public ArrayList<Member> selectByName(Connection conn, String name) {
        ArrayList<Member> list = new ArrayList<>();
        PreparedStatement pStmt = null;
        ResultSet rSet = null;

        String sql = prop.getProperty("selectByName");
        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, "%" + name + "%");
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
                        rSet.getDate("ENROLLDATE"),
                        rSet.getString("DEL_FLAG"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rSet);
            close(pStmt);
        }
        return list;
    }

    public int updateMember(Connection conn, Member mem) {
        int result = 0;
        PreparedStatement pStmt = null;
        String sql = prop.getProperty("updateMember");
        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, mem.getPassword());
            pStmt.setString(2, mem.getEmail());
            pStmt.setString(3, mem.getPhone());
            pStmt.setString(4, mem.getAddress());
            pStmt.setString(5, mem.getUserId());

            result = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            close(pStmt);
        }

        return result;
    }

    public Member login(Connection conn, Member m) {
        Member mem = null;

        PreparedStatement pStmt = null;

        ResultSet rSet = null;

        String sql = prop.getProperty("login");
        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, m.getUserId());
            pStmt.setString(2, m.getPassword());
            rSet = pStmt.executeQuery();
            if (rSet.next()) {
                mem = new Member(rSet.getString("USERID"),
                        rSet.getString("PASSWORD"),
                        rSet.getString("USERNAME"),
                        rSet.getString("GENDER"),
                        rSet.getInt("AGE"),
                        rSet.getString("EMAIL"),
                        rSet.getString("PHONE"),
                        rSet.getString("ADDRESS"),
                        rSet.getString("HOBBY"),
                        rSet.getDate("ENROLLDATE")
                );
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rSet);
            close(pStmt);

        }
        return mem;
    }
//    삭제의 경우 두가지 방식으로 나눌 수 있음.
    // 1. 실제로 테이블에서 삭제하면서, 삭제회원테이블로 insert(이동)
    // 2. Member테이블에 flag컬럼을 두고 탈퇴한 회원여부를 정의
}
