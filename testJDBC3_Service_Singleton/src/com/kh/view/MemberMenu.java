package com.kh.view;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

import java.util.ArrayList;
import java.util.Scanner;

public class MemberMenu {
    private Scanner sc = new Scanner(System.in);
    private MemberController mController = new MemberController();
    public void mainMenu() {
        int menu;
        while (true) {
            System.out.println("\n============ 회원관리 프로그램 =============");
            System.out.println("1. 회원 전체 조회");

            System.out.println("2. 회원 ID 조회");

            System.out.println("3. 회원 이름으로 검색");

            System.out.println("4. 회원 가입");

            System.out.println("5. 회원 정보 변경");

            System.out.println("6. 회원 탈퇴");

            System.out.println("9. 프로그램 끝내기");

            menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1:
                    mController.selectAll();
                    break;
                case 2:
                    mController.selectOne(inputMemberId());
                    // inputMember 하듯
                    break;
                case 3:
                    mController.selectByName(inputMemberName());
                    break;
                case 4:
                    mController.insertMember(inputMember());
                    break;
                case 5:
                    Member mem = updateMember();
                    mController.updateMember(mem);
                    break;
                case 6:
                    mController.deleteMember(inputMemberId());
                    break;
                case 9:
                    System.out.print("정말로 끝내겠습니까?(y/n) : ");
                    if (sc.next().toLowerCase().charAt(0) == 'y') {
                        return;
                    }
                    break;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");

            }
        }

    }
    public Member inputMember() {
        System.out.print("아이디 : ");
        String id = sc.nextLine();
        System.out.print("암호 : ");
        String pwd = sc.nextLine();
        System.out.print("이름 : ");
        String name = sc.nextLine();
        System.out.print("성별(M/F) : ");
        String gender = sc.nextLine().toUpperCase();
        System.out.print("나이 : ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("이메일 : ");
        String email = sc.nextLine();
        System.out.print("전화번호(-빼고입력): ");
        String phone = sc.nextLine();
        System.out.print("주소 : ");
        String address = sc.nextLine();
        System.out.print("취미(,로 공백없이 나열): ");
        String hobby = sc.nextLine();

        Member m = new Member(id,pwd,name,gender,age,email,phone,address,hobby);
        return m;
    }
    private String inputMemberId() {
        System.out.print("조회하려는 아이디 : ");
        String id = sc.nextLine();
        return id;
    }
    public String inputMemberName() {
        System.out.println("조회할 회원 이름 입력: ");
        return sc.nextLine();
    }
    public Member updateMember(){
        Member m = new Member();
        m.setUserId(inputMemberId());
        System.out.print("변경할 암호 : ");
        m.setPassword(sc.next());
        System.out.print("변경할 이메일 : ");
        m.setEmail(sc.next());
        System.out.print("변경할 전화번호(-빼고입력) : ");
        m.setPhone(sc.next());
        System.out.print("변경할 주소 : ");
        sc.nextLine();
        m.setAddress(sc.nextLine());
        return m;


    }
    public void displaySuccess(String message){
        System.out.println("서비스 요청 성공 : "+message);
    }
    public void displayError(String message){
        System.out.println("서비스 요청 실패 : " + message);
    }
    /*
     * 전체 회원 출력하는 메소드
     * @param list 회원전체정보
     * */
    public void displayMemberList(ArrayList<Member> list) {
        System.out.println("\n조회된 전체 회원정보는 다음과 같습니다.");
        System.out.println("아이디\t이름\t성별\t나이\t이메일\t전화번호\t주소\t취미\t가입일");
        System.out.println("------------------------------------------------------------");
        for (Member m:list){
            System.out.println(m);
        }
    }

    public void displayMember(Member m) {
        System.out.println("\n조회된 전체 회원정보는 다음과 같습니다.");
        System.out.println("아이디\t이름\t성별\t나이\t이메일\t전화번호\t주소\t취미\t가입일");
        System.out.println("------------------------------------------------------------");
        System.out.println(m);
        System.out.println(m.getUserId().getClass());
    }
}

