package com.syu.capstone_stock.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.syu.capstone_stock.dto.KakaoUserInfoResponseDto;
import com.syu.capstone_stock.dto.MemberRequestDto;
import com.syu.capstone_stock.service.KakaoService;
import com.syu.capstone_stock.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    private final MemberService memberService;

    //@GetMapping("/kakao-login")N
    @GetMapping("/api/auth/callback/kakao")
    public String callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        MemberRequestDto mrDto = new MemberRequestDto();
        mrDto.setLoginId(userInfo.getKakaoAccount().getEmail());
        mrDto.setEmail(userInfo.getKakaoAccount().getEmail());
        mrDto.setName(userInfo.getProperties().get("nickname"));
        memberService.saveMember(mrDto);
        // User 로그인, 또는 회원가입 로직 추가
        return "redirect:/";
    }

    @RequestMapping(value="/logout", produces="application/json")
    public String Logout(HttpSession session){
        JsonNode node=kakaoService.Logout(session.getAttribute("token").toString());
        System.out.println("로그인 후 반환되는 아이디 = " + node.get("id"));
        return "redirect:/";
    }
}