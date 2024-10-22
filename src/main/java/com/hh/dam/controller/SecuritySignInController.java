package com.hh.dam.controller;

import com.hh.dam.dto.SignInRequest;
import com.hh.dam.dto.SignUpRequest;
import com.hh.dam.entity.Member;
import com.hh.dam.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class SecuritySignInController {

    private final MemberService memberService;

    // 홈 페이지(로그인 한 사용자만 보이는 화면)
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("signType", "security-signIn");
        model.addAttribute("pageName", "스프링 시큐리티 홈");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/signin";
        }

        String userId = authentication.getName();
        Member signInMember = memberService.getLoginMemberById(userId);
        if (signInMember != null) {
            model.addAttribute("name", signInMember.getUserId());
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> model.addAttribute("role", authority.getAuthority()));

        return "index";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("signType", "security-signUp");
        model.addAttribute("pageName", "스프링 시큐리티 회원가입");

        model.addAttribute("signUpRequest", new SignUpRequest());
        return "signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute SignUpRequest signUpRequest, BindingResult bindingResult, Model model) {
        model.addAttribute("signType", "security-signUp");
        model.addAttribute("pageName", "스프링 시큐리티 회원가입");

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "signup";
        }

        try {
            memberService.securitySignUp(signUpRequest);
        } catch (IllegalStateException e) {
            bindingResult.reject("duplicateUserId", "이미 존재하는 아이디입니다.");
            return "signup";
        }

        return "redirect:/signin";
    }


    // 로그인 페이지
    @GetMapping("/signin")
    public String signInPage(Model model) {
        model.addAttribute("signType", "security-signIn");
        model.addAttribute("pageName", "스프링 시큐리티 로그인");
        model.addAttribute("signInRequest", new SignInRequest());

        return "signin";
    }
    

    // 회원 정보 페이지
    @GetMapping("/info")
    public String memberInfo(Authentication auth, Model model) {
        model.addAttribute("signType", "security-signIn");
        model.addAttribute("pageName", "스프링 시큐리티 회원정보");

        Member signInMember = memberService.getLoginMemberById(auth.getName());
        model.addAttribute("member", signInMember);
        return "info";
    }

    // 관리자 페이지
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("signType", "security-signIn");
        model.addAttribute("pageName", "스프링 시큐리티 관리자");

        return "admin";
    }
}
