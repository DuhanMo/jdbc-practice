package com.kh.model.Service;

import com.kh.model.Dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

import java.sql.*;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;

// Service 에서는 DB연결 객체 생성, 비즈니스로직 처리, 트랜잭션처리..
public class MemberService {

    public ArrayList<Member> selectAll(){
        Connection conn = getConnection();
        ArrayList<Member> list = new MemberDao().selectAll(conn);
        close(conn);
        return list;
    }
    public Member selectOne(String id) {
        Connection conn = getConnection();
        Member m = new MemberDao().selectOne(conn,id);
        close(conn);
        return m;
    }
    public int insertMember(Member m){
//        int result = 0;
//        Connection conn = null;
//
//        try{
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
//            conn.setAutoCommit(false);
//
//            result = new MemberDao().insertMember(conn, m);
//
//            if(result>0) conn.commit();
//            else conn.rollback();
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            try{
//                conn.close();
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
        Connection conn = getConnection();
        int result = new MemberDao().insertMember(conn, m);
        if(result>0){
            commit(conn);
        }else rollback(conn);
        close(conn);
        return result;
    }


    public ArrayList<Member> selectByName(String name) {
        Connection conn = getConnection();

        ArrayList<Member> list = new MemberDao().selectByName(conn, name);
        close(conn);
        return list;
    }

    public int updateMember(Member mem) {
        Connection conn = getConnection();
        int result = new MemberDao().updateMember(conn, mem);
        if(result>0){
            commit(conn);
        }else rollback(conn);
        close(conn);
        return result;
    }

    public int deleteMember(String id) {
        Connection conn = getConnection();
        int result = new MemberDao().deleteMember(conn,id);
        if(result>0){
            commit(conn);
        }else rollback(conn);
        close(conn);
        return result;
    }
}



