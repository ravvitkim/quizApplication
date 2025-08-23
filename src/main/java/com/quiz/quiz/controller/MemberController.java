package com.quiz.quiz.controller;

import com.quiz.quiz.dto.MemberDto;
import com.quiz.quiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "user/memberList";
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
        System.out.println("회원가입 요청됨: " + dto);
        memberService.saveMember(dto);
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
            // 세션 갱신
            session.setAttribute("loginEmail", loginResult.getId());
            session.setAttribute("loginStatus", loginResult.isStatus());
            session.setMaxInactiveInterval(60 * 30);

            if ("root".equals(dto.getId()) && "admin".equals(dto.getPassword())) {
                return "redirect:/list";
            }

            if (!loginResult.isStatus()) {
                return "redirect:/wait";  // 승인 대기 화면
            } else {
                return "redirect:/quiz/play";  // 승인 완료
            }
        } else {
            return "user/login";
        }
    }



    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
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
    @GetMapping("/update")
    public String updateUserForm(@RequestParam("id") String id, Model model) {
        MemberDto member = memberService.findOneMember(id);
        model.addAttribute("member", member);
        return "user/userUpdate";
    }

    // 회원 수정 처리
    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("id") String id,
                             @RequestParam("password") String newPassword,
                             HttpSession session) {

        memberService.updatePassword(id, newPassword);

        // 로그인한 유저라면 세션 갱신
        String loginId = (String) session.getAttribute("loginEmail");
        if (loginId != null && loginId.equals(id)) {
            MemberDto updated = memberService.findOneMember(id);
            session.setAttribute("loginStatus", updated.isStatus()); // status 그대로
        }

        return "redirect:/list";
    }



    // 회원 삭제
    @PostMapping("/delete")
    public String userDelete(@RequestParam("id") String id) {
        memberService.deleteMember(id);
        return "redirect:/list";
    }


// 관리
@PostMapping("/user/toggleStatus")
@ResponseBody
public String toggleStatus(@RequestParam("id") String id, HttpSession session) {
    String loginId = (String) session.getAttribute("loginEmail");

    // root만 승인 가능
    if (!"root".equals(loginId)) {
        return "unauthorized";
    }

    try {
        memberService.toggleStatus(id);
        return "success";
    } catch (Exception e) {
        return "fail";
    }
}




}
