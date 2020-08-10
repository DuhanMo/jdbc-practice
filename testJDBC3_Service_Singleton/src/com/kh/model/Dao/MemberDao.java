package com.kh.model.Dao;

import com.kh.model.vo.Member;
import sun.tools.tree.Vset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDao {
    public ArrayList<Member> selectAll(Connection conn){
        ArrayList<Member> list = new ArrayList<>();
        PreparedStatement pStmt = null;
        ResultSet rset = null;
        String sql = "SELECT * FROM MEMBER";
        try{
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
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rset.close();
                pStmt.close();

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        return list;
    }

    public Member selectOne(Connection conn, String id) {
        Member m = new Member();
        PreparedStatement pStmt = null;
        ResultSet rSet = null;
        String sql = "SELECT * FROM MEMBER WHERE USERID=?";
        try{
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
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            try{
                rSet.close();
                pStmt.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return m;
    }
    public int insertMember(Connection conn, Member m) {
        int result = 0;
        PreparedStatement pStmt = null;
        String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
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
                conn.commit();
            } else conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public ArrayList<Member> selectByName(Connection conn, String name) {
        ArrayList<Member> list = new ArrayList<>();
        PreparedStatement pStmt = null;
        ResultSet rSet = null;
        String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
        try{
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,"%"+name+"%");
            rSet = pStmt.executeQuery();
            while(rSet.next()){
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
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rSet.close();
                pStmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    return list;
    }

    public int updateMember(Connection conn, Member mem) {
        int result = 0;
        PreparedStatement pStmt = null;
        String sql = "UPDATE MEMBER SET PASSWORD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID = ?";

        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, mem.getPassword());
            pStmt.setString(2, mem.getEmail());
            pStmt.setString(3, mem.getPhone());
            pStmt.setString(4, mem.getAddress());
            pStmt.setString(5, mem.getUserId());

            result = pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{

                pStmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return result;
    }

    public int deleteMember(Connection conn, String id) {
        int result = 0;
        PreparedStatement pStmt = null;
        String sql = "DELETE FROM MEMBER WHERE USERID=?";
        try{
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,id);
            result = pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                pStmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        return result;
    }
}