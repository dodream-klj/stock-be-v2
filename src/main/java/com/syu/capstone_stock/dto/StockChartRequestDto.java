package com.syu.capstone_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 주식 차트 데이터를 요청하는 파라미터
 */
@Data
public class StockChartRequestDto {
    @Schema(description = "차트 데이터 시작일", example = "2024-11-05")
    private String startDate;

    @Schema(description = "차트 데이터 종료일", example = "2024-11-15")
    private String endDate;
}
