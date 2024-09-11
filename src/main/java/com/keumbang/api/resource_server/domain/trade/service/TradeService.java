package com.keumbang.api.resource_server.domain.trade.service;

import com.keumbang.api.resource_server.domain.product.entity.Product;
import com.keumbang.api.resource_server.domain.product.repository.ProductRepository;
import com.keumbang.api.resource_server.domain.trade.dto.TradeRequestDto;
import com.keumbang.api.resource_server.domain.trade.entity.Trade;
import com.keumbang.api.resource_server.domain.trade.enums.TradeStatus;
import com.keumbang.api.resource_server.domain.trade.repository.TradeRepository;
import com.keumbang.api.resource_server.global.common.exception.CustomException;
import com.keumbang.api.resource_server.global.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final ProductRepository productRepository;

    private String generateTradeNumber(Trade trade) {
        // 1. 날짜 형식을 yyMMdd로 설정
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

        // 2. 상품 타입 코드를 가져옴 (예: G9999)
        String productCode = trade.getProduct().getType().getCode();

        // 3. 시퀀스 번호를 6자리로 포맷팅
        String formattedSequenceNumber = String.format("%08d", trade.getId());

        // 4. 최종 주문 번호 조합
        return datePart + "-" + productCode + "-" + formattedSequenceNumber;
    }

    @Transactional
    public Long createTrade(TradeRequestDto tradeRequestDto) {
        Product product = productRepository.findById(tradeRequestDto.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        BigDecimal totalCost = BigDecimal.valueOf(product.getPrice().doubleValue() * tradeRequestDto.getQuantity().doubleValue());

        Trade trade = Trade.builder()
                .userId(tradeRequestDto.getUserId())
                .product(product)
                .quantity(tradeRequestDto.getQuantity())
                .cost(totalCost.longValue())
                .shippingAddress(tradeRequestDto.getShippingAddress())
                .type(tradeRequestDto.getType())
                .status(TradeStatus.createOrderOf(tradeRequestDto.getType()))
                .build();

        tradeRepository.save(trade);
        tradeRepository.updateTradeNumber(trade.getId(), generateTradeNumber(trade));
        return trade.getId();
    }
}
