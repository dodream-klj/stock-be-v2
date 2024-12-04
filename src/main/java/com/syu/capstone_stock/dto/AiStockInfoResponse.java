package com.syu.capstone_stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AiStockInfoResponse {

    @Schema(description = "주식 코드", example = "005930")
    private String code;

    @Schema(description = "매수 신호. true면 매수, false면 매수하지 않음", example = "true")
    private boolean buy;
}
