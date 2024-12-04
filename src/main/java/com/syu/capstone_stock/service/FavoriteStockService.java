package com.syu.capstone_stock.service;

import com.syu.capstone_stock.domain.FavoriteStock;
import com.syu.capstone_stock.dto.FavoriteStockRequestDto;
import com.syu.capstone_stock.dto.StockInfoResponse;
import com.syu.capstone_stock.repositry.FavoriteStockRepository;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
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
    private final PyService pyService;

    @Transactional
    public ResponseEntity<?> addFavoriteStock(String loginId, String code) {
        FavoriteStock favoriteStock = new FavoriteStock();
        favoriteStock.setCode(code);
        favoriteStock.setLoginId(loginId);

        if (favoriteStockRepository.existsByLoginIdAndCode(loginId, code)) {
            return ResponseEntity.badRequest().body("이미 추가된 관심종목입니다.");
        }

        if (favoriteStockRepository.save(favoriteStock).getId() > 0) {
            return ResponseEntity.ok().body("관심종목으로 추가되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteFavoriteStock(String loginId, String id) {
        favoriteStockRepository.deleteFavoriteStockByLoginIdAndCode(loginId, id);
        return ResponseEntity.ok().body("관심종목 삭제 성공");
    }

    public List<StockInfoResponse> selectFavoriteStockListByLoginId(String loginId) {
        List<FavoriteStock> favoriteStocks = favoriteStockRepository.findAllByLoginId(loginId);

        String result = favoriteStocks.stream()
            .map(FavoriteStock::getCode)
            .collect(Collectors.joining(","));

        return pyService.findStockInfoFavorite(result);

    }
}
