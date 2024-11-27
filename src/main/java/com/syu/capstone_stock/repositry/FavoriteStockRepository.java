package com.syu.capstone_stock.repositry;

import com.syu.capstone_stock.domain.FavoriteStock;
import com.syu.capstone_stock.domain.Member;
import com.syu.capstone_stock.dto.FavoriteStockRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteStockRepository extends JpaRepository<FavoriteStock, Long> {
    Member findByDeleteYnAndLoginId(Boolean b, String loginId);
    Integer countByLoginId(String loginId);
    Member findByEmailAndMailauth(String email, Boolean b);
    List<FavoriteStockRequestDto> findAllByLoginId(String loginId);
}