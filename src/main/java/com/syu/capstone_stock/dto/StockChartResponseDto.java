package com.syu.capstone_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 주식 차트 데이터를 담는 DTO
 */
@Data
public class StockChartResponseDto {
    @JsonProperty("Date")
    private String date;

    @JsonProperty("Open")
    private Double open;

    @JsonProperty("High")
    private Double high;

    @JsonProperty("Low")
    private Double low;

    @JsonProperty("Close")
    private Double close;

    @JsonProperty("Volume")
    private Double volume;

    @JsonProperty("Change")
    private Double change;
}
