package com.keumbang.api.resource_server.domain.trade.repository;

import com.keumbang.api.resource_server.domain.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query(value = "UPDATE trade SET trade_number = :tradeNumber WHERE id = :id", nativeQuery = true)
    Integer updateTradeNumber(Long id, String tradeNumber);
}
