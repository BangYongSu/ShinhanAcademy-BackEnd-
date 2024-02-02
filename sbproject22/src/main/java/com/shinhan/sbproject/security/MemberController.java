package com.shinhan.sbproject.security;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.sbproject.vo.MemberDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

	
    private final MemberService memberService;
    
    
    @GetMapping("/signup")  //회원가입 창
    public String signUp() {
    	 return "auth/joinForm";
    }
    @PostMapping("/joinProc")
    public @ResponseBody String signUp(MemberDTO member) {
    	System.out.println(member);
    	memberService.joinUser(member); //암호화하고 저장
        return "OK" ;
    }
    @GetMapping("/login")
    public void f1() {}

    @GetMapping("/loginSuccess")
    public void f3() { }
    @GetMapping("/logout") 
    public void f4() { }
    @GetMapping("/accessDenined") //권한이 없는데 들어갔다면?!?!??!?!?!?
    public void f5(){}

}