package com.syu.capstone_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 주식 차트 데이터를 담는 DTO
 */
@Data
public class ExchangesResponseDto {
    @JsonProperty("Open")
    private Double open;

    @JsonProperty("High")
    private Double high;

    @JsonProperty("Low")
    private Double low;

    @JsonProperty("Close")
    private Double close;

    @JsonProperty("Volume")
    private Integer volume;

    @JsonProperty("Adj Close")
    private Double adjClose;

    @JsonProperty("Name")
    private String name;
}
