package com.syu.capstone_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 주식 이름 정보를 담는 DTO
 */
@Data
public class StockNameInfoResponseDto {
    @JsonProperty("Code")
    private String code;

    @JsonProperty("Name")
    private String name;
}
