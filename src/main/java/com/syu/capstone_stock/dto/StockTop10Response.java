package com.syu.capstone_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StockTop10Response {

    @JsonProperty("Code")
    @Schema(description = "주식 코드", example = "005930")
    private String Code;

    @JsonProperty("ISU_CD")
    @Schema(description = "상장 종목 코드", example = "KR7005930003")
    private String ISU_CD;

    @JsonProperty("Name")
    @Schema(description = "주식 이름", example = "삼성전자")
    private String Name;

    @JsonProperty("Dept")
    @Schema(description = "부서", example = "기술")
    private String Dept;

    @JsonProperty("ChangeCode")
    @Schema(description = "변경 코드", example = "1")
    private String ChangeCode;

    @JsonProperty("ChagesRatio")
    @Schema(description = "변동 비율", example = "1.2")
    private String ChagesRatio;

    @JsonProperty("Market")
    @Schema(description = "시장 이름", example = "KOSPI")
    private String Market;

    @JsonProperty("Close")
    @Schema(description = "종가", example = "59000")
    private String Close;

    @JsonProperty("Changes")
    @Schema(description = "변화 금액", example = "700")
    private String Changes;

    @JsonProperty("ChangesRatio")
    @Schema(description = "변화 비율", example = "1.2")
    private double ChangesRatio;

    @JsonProperty("Open")
    @Schema(description = "시가", example = "58600")
    private String Open;

    @JsonProperty("High")
    @Schema(description = "최고가", example = "59400")
    private String High;

    @JsonProperty("Low")
    @Schema(description = "최저가", example = "58500")
    private String Low;

    @JsonProperty("Volume")
    @Schema(description = "거래량", example = "9699591")
    private String Volume;

    @JsonProperty("Amount")
    @Schema(description = "거래 금액", example = "571652151700")
    private String Amount;

    @JsonProperty("Marcap")
    @Schema(description = "시가총액", example = "352217170450000")
    private String Marcap;

    @JsonProperty("Stocks")
    @Schema(description = "주식 수", example = "5969782550")
    private String Stocks;

    @JsonProperty("MarketId")
    @Schema(description = "시장 ID", example = "STK")
    private String MarketId;
}
