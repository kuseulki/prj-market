package com.hellomarket.entity;

import com.hellomarket.dto.MemberDto;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**     회원정보    */

@Entity
@Getter @Setter
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;              // 아이디

    @Column(nullable = false, length = 100)
    private String password;            // 비밀번호

    @Column(nullable = false)
    private String userName;            // 이름

    @Column(nullable = false)
    private String email;               // 이메일

    @Column(nullable = false, length = 11)
    private String phone;               //휴대폰

    @Column(nullable = false)
    private String address;          // 주소

    private String role;                // USER, ADMIN

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Cart cart;


    // Member 엔티티를 생성하는 메소드
    public static Member createMember(MemberDto memberDto, BCryptPasswordEncoder encoderPwd){
        Member member = new Member();
        member.setUserId(memberDto.getUserId());
        String password = encoderPwd.encode(memberDto.getPassword());
        member.setPassword(password);
        member.setUserName(memberDto.getUserName());
        member.setEmail(memberDto.getEmail());
        member.setPhone(memberDto.getPhone());
        member.setAddress(memberDto.getAddress());
        member.setRole("ROLE_ADMIN");
        return member;
    }
}
