package com.kh.model.vo;


import java.io.Serializable;
import java.util.Date;

/*
 * VO(Value Object)
 * DB 테이블의 한 행의 정보가 기록되는 저장용 객체
 *
 * *유사 용어
 *  DTO (Date Transfer Object)
 *  DO (Domain Object)
 *  Entity : 스트럭쳐 안에서 사용하는 용어
 *  bean -> EJB(Enterprise JavaBeans)는 기업환경의 시스템을 구현하기 위한 서버측 컴포넌트 모델
 *  * VO 조건
 *  1) 반드시 캡슐화를 적용할 것 : 모든 필드는 private
 *  2) 기본생성자와 매개변수 생성자 작성하자
 *  3) 모든 필드에 대한 getter/setter 가 필요
 *  4) 직렬화 처리(네트워크 상의 데이터처리를 위함)
 *       --> 40Byte의 객체를 파일 입출력 단위로 직렬화, 수신측에서 역직렬화시 여러 객체를 구분할
 *           고유 아이디가 필요하다. 없으면 framework 작업에서 에러유발가능성 상승.
 *  5)
 * */
public class Member implements Serializable {
    private static final long serialVersionUID = 3756974891965732632L;
//    private static final long serialVersionUID = 3756974891965732632L;
//    private static final long serialVersionUID = -9020086208371395560L;

    // 필드 : DB컬럼 정보와 동일하게 작업(필수는 아님)
    private String userId;
    private String password;
    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    private String address;
    private String hobby;
    private Date enrollDate;

    public Member() {
    }

    // 회원 가입용
    public Member(String userId, String password, String userName, String gender, int age, String email, String phone, String address, String hobby) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.hobby = hobby;
        // enrollDate --> sql insert 구문에서 sysdate 로 대체
    }

    // 회원 조회용
    public Member(String userId, String password, String userName, String gender, int age, String email, String phone, String address, String hobby, Date enrollDate) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.hobby = hobby;
        this.enrollDate = enrollDate;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", hobby='" + hobby + '\'' +
                ", enrollDate=" + enrollDate +
                '}';
    }
}
