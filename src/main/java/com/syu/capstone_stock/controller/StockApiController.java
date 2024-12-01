package com.syu.capstone_stock.controller;

import com.syu.capstone_stock.dto.ExchangesResponseDto;
import com.syu.capstone_stock.dto.StockChartRequestDto;
import com.syu.capstone_stock.dto.StockChartResponseDto;
import com.syu.capstone_stock.dto.StockNameInfoResponseDto;
import com.syu.capstone_stock.dto.StockInfoResponse;
import com.syu.capstone_stock.service.PyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", maxAge = 3000, allowCredentials = "true")
public class StockApiController {
    private final PyService pyService;

    @Operation(
        summary = "거래량 상위 TOP 10 종목 조회",
        description = "거래량 상위 TOP 10 종목을 조회합니다."
    )
    @GetMapping("/caps/info/top10")
    public List<StockInfoResponse> findStockTop10() {
        return pyService.findStockTop10();
    }

    @Operation(
        summary = "주식 전체 종목 조회"
    )
    @GetMapping("/caps/info/stocks")
    public List<StockNameInfoResponseDto> findAllStock() {
        return pyService.findAllStock();
    }

    @Operation(
        summary = "환율 정보 조회",
        description = "달러/유로, 달러/엔화, 달러/원, 달러/위안, 원화/엔화 정보를 조회합니다."
    )
    @GetMapping("/caps/info/exchanges")
    public List<ExchangesResponseDto> findExchange(){
        return pyService.findExchange();
    }

    @Operation(
        summary = "주식 차트 데이터 조회",
        description = "종목 코드와 날짜를 기준으로 주식 차트 데이터를 조회합니다."
    )
    @GetMapping("/caps/stocks/{code}/chart")
    public List<StockChartResponseDto> saveStockKRX(
        @Parameter(description = "조회할 종목 코드 (예: 005930)", example = "005930") @PathVariable String code
        , StockChartRequestDto stockChartRequestDto
    ) {
        return pyService.getStockChartData(code, stockChartRequestDto);
    }

    @Operation(
        summary = "주식 단일 정보 조회",
        description = "종목 코드로 주식 단일 정보를 조회합니다."
    )
    @GetMapping("/caps/stocks/{code}")
    public StockInfoResponse findStockInfo(
        @Parameter(description = "조회할 종목 코드 (예: 005930)", example = "005930") @PathVariable String code
    ) {
        return pyService.findStockInfo(code);
    }

    @Operation(
        summary = "주식 Like 조건 정보 검색",
        description = "종목명으로 주식 검색 Like 결과 반환."
    )
    @GetMapping("/caps/stocks/search/{name}")
    public List<StockInfoResponse> findStockInfoLikeSearch(
        @Parameter(description = "조회할 종목명 (예: 삼성)", example = "삼성") @PathVariable String name
    ) {
        return pyService.findStockInfoLikeSearch(name);
    }
}
