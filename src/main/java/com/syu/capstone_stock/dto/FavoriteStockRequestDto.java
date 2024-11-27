package com.syu.capstone_stock.dto;

import com.syu.capstone_stock.domain.FavoriteStock;
import com.syu.capstone_stock.domain.Gender;
import com.syu.capstone_stock.domain.Member;
import com.syu.capstone_stock.util.EncryptionUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class FavoriteStockRequestDto {
    private Long id;
    private String code;
    private String loginId;

    public FavoriteStock toEntity(){
        return FavoriteStock.builder()
                .code(this.code)
                .loginId(this.loginId)
                .build();
    }
}
