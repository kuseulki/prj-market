package com.hellomarket.controller;

import com.hellomarket.dto.MemberDto;
import com.hellomarket.entity.Item;
import com.hellomarket.entity.Member;
import com.hellomarket.service.ItemService;
import com.hellomarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final BCryptPasswordEncoder encoderPwd;

    @GetMapping("/")
    public String main(Model model) {
        List<Item> items = itemService.allItemView();
        model.addAttribute("items", items);
        return "main";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "members/login";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "members/join";
    }

    @PostMapping("/join")
    public String login(@Validated MemberDto memberDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "members/join";
        }
        try {
            Member member = Member.createMember(memberDto, encoderPwd);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/join";
        }
        return "redirect:/";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/members/login";
    }
}