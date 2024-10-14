package com.hh.dam.controller;

import com.hh.dam.entity.Member;
import com.hh.dam.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hh.dam.repository.MemberRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final MemberService memberService;

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }
//    private final MemberRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserController(MemberRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "redirect:/login";
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }

    @GetMapping("/login")  // 로그인 페이지에 대한 GET 요청 처리
    public String loginPage() {
        return "login";  // 로그인 페이지 반환
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userId, @RequestParam String password) {
        Member member = memberService.findByUserId(userId);
        if (member != null && memberService.checkPassword(password, member.getPassword())) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

}
