package com.syu.capstone_stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syu.capstone_stock.dto.ExchangesResponseDto;
import com.syu.capstone_stock.dto.StockChartRequestDto;
import com.syu.capstone_stock.dto.StockChartResponseDto;
import com.syu.capstone_stock.dto.StockNameInfoResponseDto;
import com.syu.capstone_stock.dto.StockTop10Response;
import com.syu.capstone_stock.util.PythonExec;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PyService {
    private List<String> result = null;
    private final ObjectMapper objectMapper;

    public List<StockTop10Response> findStockTop10() {
        result = PythonExec.exec("top_10_stock.py");

        List<StockTop10Response> stockTop10Responses = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String jsonString : result) {
            try {
                System.out.println(jsonString);
                // 각 JSON 문자열을 StockDto 객체로 변환하여 List에 추가
                stockTop10Responses = objectMapper.readValue(jsonString, new TypeReference<List<StockTop10Response>>() {});
                log.info("stockTop10Responses: {}", stockTop10Responses);

            } catch (Exception e) {
                log.error("JSON 문자열을 객체로 변환하는 데 실패했습니다.", e);
            }
        }

        return stockTop10Responses;
    }

    public void saveGraph(final String Code) {
        PythonExec.exec("graph.py",Code);
    }

    public String findStockInfo(String Code) {
        result = PythonExec.exec("stock_info.py", Code);
        return iterList(result);
    }

    public List<StockNameInfoResponseDto> findAllStock() {
        String result = PythonExec.execByzt("all_stock_code_name.py");

        List<StockNameInfoResponseDto> stockNameInfoResponseDtos;

        try {
            stockNameInfoResponseDtos = objectMapper.readValue(result, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("stockNameInfoResponseDtos: {}", stockNameInfoResponseDtos);

        return stockNameInfoResponseDtos;
    }

    public List<ExchangesResponseDto> findExchange(){
        result =  PythonExec.exec("exchange_rate.py");

        List<ExchangesResponseDto> exchangesResponseDtos = new ArrayList<>();

        for (String jsonString : result) {
            try {
                System.out.println(jsonString);
                exchangesResponseDtos = objectMapper.readValue(jsonString, new TypeReference<>() {});
                log.info("exchangesResponseDtos: {}", exchangesResponseDtos);

            } catch (Exception e) {
                log.error("JSON 문자열을 객체로 변환하는 데 실패했습니다.", e);
            }
        }

        return exchangesResponseDtos;
    }

    private String iterList(List<String> arg) {
        String res = null;
        for(String s : arg){
            res = s+"\n";
        }
        return res;
    }

    public List<StockChartResponseDto> getStockChartData(String code, StockChartRequestDto stockChartRequestDto) {
        String startDate = stockChartRequestDto.getStartDate();
        String endDate = stockChartRequestDto.getEndDate();

        result = PythonExec.exec("get_stock_data.py", code, startDate, endDate);

        List<StockChartResponseDto> stockChartResponseDto = new ArrayList<>();

        for (String jsonString : result) {
            try {
                System.out.println(jsonString);
                stockChartResponseDto = objectMapper.readValue(jsonString, new TypeReference<>() {});
                log.info("stockChartResponseDto: {}", stockChartResponseDto);

            } catch (Exception e) {
                log.error("JSON 문자열을 객체로 변환하는 데 실패했습니다.", e);
            }
        }

        return stockChartResponseDto;
    }
}