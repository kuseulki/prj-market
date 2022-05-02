package com.hellomarket.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
//    @Length(min = 3, max = 16, message = "비밀번호는 3자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotEmpty(message = "숫자만 입력해주세요")
    private String phone;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;
}