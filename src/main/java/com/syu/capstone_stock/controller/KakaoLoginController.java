package com.syu.capstone_stock.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.syu.capstone_stock.dto.KakaoUserInfoResponseDto;
import com.syu.capstone_stock.service.KakaoService;
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

    //@GetMapping("/kakao-login")
    @GetMapping("/api/auth/callback/kakao")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        System.out.println("accessToken info  = " + accessToken);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        System.out.println(" userInfo user nickname info   = " + userInfo.getProperties().get("nickname"));
        // User 로그인, 또는 회원가입 로직 추가
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/logout", produces="application/json")
    public String Logout(HttpSession session){
        JsonNode node=kakaoService.Logout(session.getAttribute("token").toString());
        System.out.println("로그인 후 반환되는 아이디 = " + node.get("id"));
        return "redirect:/";
    }
}