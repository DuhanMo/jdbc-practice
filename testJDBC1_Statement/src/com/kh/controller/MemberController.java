package com.kh.controller;

import com.kh.model.Dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

import java.util.ArrayList;

// Controller : View 에서 전달받은 데이터를 가공처리(데이터 변환 및 디코딩) 후 Dao로 전달하는 기능
//              Dao 로부터 반환받은 결과에 따라 View(출력할 화면)을 결정하여 인코딩 후 데이터 화면에 전달
public class MemberController {
    /*
    * 1. 전체 회원 조회
    *
    * */
    public void selectAll() {
        ArrayList<Member> list = new MemberDao().selectAll();
        // list는 결과값이 있거나, 비어있는 객체이다. null 일 수는 없다
        if(list.isEmpty()){
            new MemberMenu().displayError("해당되는 데이터가 없습니다.");
        }else {
            new MemberMenu().displayMemberList(list);
        }
    }

    /*2. 회원 아이디로 조회
    * @param inputMemberId 회원아이디
    * */
    public void selectOne(String id){
        Member m = new MemberDao().selectOne(id);
        if (m.getUserId()!=null){
            new MemberMenu().displayMember(m);
        }else{
            new MemberMenu().displayError(id+"에 해당하는 데이터가 없습니다.");
        }
    }
/*
*  4. View 에서 전달받은 데이터를 가공처리 후 Dao 로 전달한 후 그 결과를 받아 화면에 출력
*       -> 회원가입용 기능 메소드
*
*  @param m
* */
    public void insertMember(Member m) {
        int result = new MemberDao().insertMember(m);
        if(result>0) {
        new MemberMenu().displaySuccess("회원가입성공"); // 성공메시지 출력 View 호출
        }else{
            new MemberMenu().displayError("회원가입 실패"); // 실패메세지 출력 view 호출
        }
    }

    /*3.회원 이름으로 조회
    * @param name
    * */


    public void selectByName(String name) {
        ArrayList<Member> list = new MemberDao().selectByName(name);
        if(list.isEmpty()){
            new MemberMenu().displayError(name + "에 해당하는 데이터가 없습니다.");
        }else{
            new MemberMenu().displayMemberList(list);
        }
    }

    public void deleteMember(String id) {
        int result = new MemberDao().deleteMember(id);
        if(result>0) {
            new MemberMenu().displaySuccess("회원 탈퇴 완료"); // 성공메시지 출력 View 호출
        }else{
            new MemberMenu().displayError("회원 탈퇴 실패"); // 실패메세지 출력 view 호출
        }
    }


    public void updateMember(Member m) {
        int result = new MemberDao().updateMember(m);
        if(result>0) {
            new MemberMenu().displaySuccess("회원 수정 완료"); // 성공메시지 출력 View 호출
        }else{
            new MemberMenu().displayError("회원 수정 실패"); // 실패메세지 출력 view 호출
        }
    }
}
