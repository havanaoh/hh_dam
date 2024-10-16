package com.hh.dam.controller;

import com.hh.dam.entity.Member;
import com.hh.dam.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 정보 조회
    @GetMapping("/info/{userId}")
    public String showMemberInfo(@PathVariable String userId, Model model) {
        Member member = memberService.getLoginMemberById(userId);  // userId로 회원 정보 조회

        if (member == null) {
            model.addAttribute("errorMessage", "회원 정보를 찾을 수 없습니다.");
            return "error"; // 에러 페이지로 리디렉션
        }

        model.addAttribute("member", member);
        return "info"; // 회원 정보 페이지
    }

    // 회원 정보 수정 페이지로 이동
    @GetMapping("/info/update/{userId}")
    public String showUpdateMemberForm(@PathVariable String userId, Model model) {
        Member member = memberService.getLoginMemberById(userId); // userId로 회원 정보 조회

        if (member == null) {
            model.addAttribute("errorMessage", "회원 정보를 찾을 수 없습니다.");
            return "error"; // 에러 페이지로 리디렉션
        }

        model.addAttribute("member", member);
        return "updateInfo"; // 회원 정보 수정 폼 페이지
    }

    // 회원 정보 수정 처리
    @PostMapping("/member/update")
    public String updateMemberInfo(Member member, Model model) {
        // 비밀번호 암호화가 필요한 경우 암호화 로직 추가
        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            member.setPassword(memberService.encodePassword(member.getPassword()));
        }

        memberService.updateMember(member); // 회원 정보 수정 처리

        model.addAttribute("member", member);
        return "redirect:/member/" + member.getUserId(); // 수정 후 회원 정보 페이지로 리디렉션
    }
}
