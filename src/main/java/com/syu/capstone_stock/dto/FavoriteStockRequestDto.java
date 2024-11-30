package com.syu.capstone_stock.dto;

import com.syu.capstone_stock.domain.FavoriteStock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FavoriteStockRequestDto {
    private String code;
    private String loginId;

    public FavoriteStock toEntity(){
        return FavoriteStock.builder()
                .code(this.code)
                .loginId(this.loginId)
                .build();
    }
}
