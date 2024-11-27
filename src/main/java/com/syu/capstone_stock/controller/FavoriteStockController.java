package com.syu.capstone_stock.controller;

import com.syu.capstone_stock.domain.Member;
import com.syu.capstone_stock.dto.FavoriteStockRequestDto;
import com.syu.capstone_stock.dto.MemberRequestDto;
import com.syu.capstone_stock.service.FavoriteStockService;
import com.syu.capstone_stock.service.MemberService;
import com.syu.capstone_stock.util.ClientUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", maxAge = 3000, allowCredentials = "true")
public class FavoriteStockController {
    private final FavoriteStockService favoriteStockService;

    @PostMapping("/favorite/stock/add")
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody final FavoriteStockRequestDto favoriteStockRequestDto, HttpServletRequest request){
        ClientUtils.getRemoteIP(request);
        return favoriteStockService.addFavoriteStock(favoriteStockRequestDto);
    }

    @DeleteMapping("/favorite/stock/{id}")
    @ResponseBody
    public Long deleteMemberById(final Long id){
        return favoriteStockService.deleteFavoriteStock(id);
    }

    @GetMapping("/favorite/stock/{loginId}")
    public List<FavoriteStockRequestDto> selectFavoriteStockListByLoginId(final String loginId){
        return favoriteStockService.selectFavoriteStockListByLoginId(loginId);
    }

}
