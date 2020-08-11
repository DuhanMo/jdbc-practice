package com.kh.model.Service;

import com.kh.model.Dao.MemberDao;
import com.kh.model.vo.Member;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;
public class MemberService {
    public int insertMember(Member m) {
        Connection conn = getConnection();
        int result = new MemberDao().insertMember(conn, m);

        if(result > 0){
            commit(conn);
        }else{
            rollback(conn);
        }
        return result;
    }

    public int deleteMember(String id) {
        Connection conn = getConnection();
        int result = new MemberDao().deleteMember(conn,id);
        if(result>0){
            commit(conn);
        }else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public ArrayList<Member> selectAll() {
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

    public Member login(Member m) {
        Connection conn = getConnection();
        Member mem = new MemberDao().login(conn,m);

        close(conn);
        return mem;
    }
}
