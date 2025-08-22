package com.quiz.quiz.controller;

import com.quiz.quiz.dto.MemberDto;
import com.quiz.quiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 목록 보기
    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("title", "리스트보기");
        List<MemberDto> memberList = memberService.getAllList();
        model.addAttribute("list", memberList);
        return "showMember";
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "user/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute("member") MemberDto dto) {
        memberService.saveMember(dto);  // 메서드명 통일됨
        return "redirect:/";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(MemberDto dto, HttpSession session) {
        MemberDto loginResult = memberService.findOneMember(dto.getId());
        if (loginResult == null) {
            return "user/login";
        } else if (dto.getPassword().equals(loginResult.getPassword())) {
            session.setAttribute("loginEmail", dto.getId());
            session.setMaxInactiveInterval(60 * 30);
            return "redirect:/";
        } else {
            return "user/login";
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "main";
    }

    // 내 정보 보기
    @GetMapping("/myInfo")
    public String myInfo(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        if (myEmail == null) return "redirect:/login";

        MemberDto member = memberService.findOneMember(myEmail);
        model.addAttribute("member", member);
        return "user/myInfo";
    }

    // 회원 수정 폼
    @PostMapping("/update")
    public String updateUserForm(@RequestParam("id") String id, Model model) {
        MemberDto member = memberService.findOneMember(id);
        model.addAttribute("member", member);
        return "user/userUpdate";
    }

    // 회원 수정 처리
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("member") MemberDto member) {
        memberService.saveMember(member);  // 통일된 메서드명 사용
        return "redirect:/list";
    }

    // 회원 삭제
    @PostMapping("/delete")
    public String userDelete(@RequestParam("id") String id) {
        memberService.deleteMember(id);
        return "redirect:/list";
    }
}
