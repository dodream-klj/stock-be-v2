package com.syu.capstone_stock.repositry;

import com.syu.capstone_stock.domain.FavoriteStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteStockRepository extends JpaRepository<FavoriteStock, Long> {
    void deleteFavoriteStockByLoginIdAndCode(String loginId, String code);
    List<FavoriteStock> findAllByLoginId(String loginId);
    Boolean existsByLoginIdAndCode(String loginId, String code);
}