package com.kh.controller;

import com.kh.model.Service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

import java.util.ArrayList;

public class MemberController {
    public void insertMember(Member m) {
        int result = new MemberService().insertMember(m);

        if(result > 0){
            new MemberMenu().displaySuccess("회원가입 성공");
        }else{
            new MemberMenu().displayError("회원가입 실패");
        }
    }

    public void deleteMember(String id) {
        int result = new MemberService().deleteMember(id);
        if(result > 0){
            new MemberMenu().displaySuccess("회원탈퇴 성공");
        }else{
            new MemberMenu().displayError("회원탈퇴 실패");
        }
    }

    public void selectAll() {
        ArrayList<Member> list = new MemberService().selectAll();
        if(list.isEmpty()){
            new MemberMenu().displayError("해당되는 데이터가 없습니다.");
        }else {
            new MemberMenu().displayMemberList(list);
        }
    }

    public void selectOne(String id) {
        Member m = new MemberService().selectOne(id);
        if (m!=null){
            new MemberMenu().displayMember(m);
        }else{
            new MemberMenu().displayError(id+"에 해당하는 데이터가 없습니다.");
        }
    }

    public void selectByName(String name) {
        ArrayList<Member> list = new MemberService().selectByName(name);

        if(list.isEmpty()){
            new MemberMenu().displayError(name + "에 해당하는 데이터가 없습니다.");
        }else {
            new MemberMenu().displayMemberList(list);
        }
    }

    public void updateMember(Member mem) {
        int result = new MemberService().updateMember(mem);
        if(result>0) {
            new MemberMenu().displaySuccess("회원 수정 완료"); // 성공메시지 출력 View 호출
        }else{
            new MemberMenu().displayError("회원 수정 실패"); // 실패메세지 출력 view 호출
        }
    }

    public void login(Member m) {
        Member loginMem = new MemberService().login(m);
        if(loginMem == null){
            new MemberMenu().displayError("로그인에 실패하였습니다.");
        }else{
            new MemberMenu().displayMyPage(loginMem);
        }
    }
}
