package com.syu.capstone_stock.controller;

import com.syu.capstone_stock.dto.FavoriteStockRequestDto;
import com.syu.capstone_stock.service.FavoriteStockService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", maxAge = 3000, allowCredentials = "true")
public class FavoriteStockController {
    private final FavoriteStockService favoriteStockService;

    @Operation(
        summary = "관심종목 추가",
        description = "종목 코드와 로그인 아이디를 인자값으로 받아 관심종목에 추가한다."
    )
    @PostMapping("/favorite/stock/{loginId}/{code}")
    public ResponseEntity<?> favoriteStockAdd(@PathVariable String loginId, @PathVariable String code){
        return favoriteStockService.addFavoriteStock(loginId, code);
    }

    @Operation(
        summary = "관심종목 가져오기",
        description = "로그인 아이디를 인자값으로 받아 관심종목을 불러온다."
    )
    @GetMapping("/favorite/stock/{loginId}")
    public List<FavoriteStockRequestDto> getFavoriteStock(@PathVariable String loginId){
        return favoriteStockService.selectFavoriteStockListByLoginId(loginId);
    }

    @Operation(
        summary = "관심종목 삭제",
        description = "종목 코드와 로그인 아이디를 인자값으로 받아 관심종목에서 삭제한다."
    )
    @DeleteMapping("/favorite/stock/{loginId}/{code}")
    public ResponseEntity<?> favoriteStockDelete(@PathVariable String loginId, @PathVariable String code){
        return favoriteStockService.deleteFavoriteStock(loginId, code);
    }

}
