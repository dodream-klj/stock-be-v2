package com.syu.capstone_stock.service;

import com.syu.capstone_stock.domain.FavoriteStock;
import com.syu.capstone_stock.domain.Member;
import com.syu.capstone_stock.dto.FavoriteStockRequestDto;
import com.syu.capstone_stock.dto.MemberRequestDto;
import com.syu.capstone_stock.repositry.FavoriteStockRepository;
import com.syu.capstone_stock.repositry.MailRepository;
import com.syu.capstone_stock.repositry.MemberRepository;
import com.syu.capstone_stock.util.EncryptionUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteStockService {
    private final FavoriteStockRepository favoriteStockRepository;
    private FavoriteStock fs;

    @Transactional
    public ResponseEntity<?> addFavoriteStock(final FavoriteStockRequestDto params){
        if(true) {
            if(favoriteStockRepository.save(params.toEntity()).getId() > 0){
                return ResponseEntity.ok().body("관심종목으로 추가되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("잘못된 요청입니다.");
            }
        } else {
            return null;
        }
    }

    private FavoriteStock findMemberById(final Long id){
        return favoriteStockRepository.findById(id).get();
    }

    @Transactional
    public Long deleteFavoriteStock(final Long id){
        if(id != null){
            fs = findMemberById(id);
            return favoriteStockRepository.save(fs).getId();
        } else {
            throw new NullPointerException();
        }
    }

    public List<FavoriteStockRequestDto> selectFavoriteStockListByLoginId(final String loginId){
        return favoriteStockRepository.findAllByLoginId(loginId);
    }
}
