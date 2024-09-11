package com.keumbang.api.resource_server.domain.trade.controller;

import com.keumbang.api.resource_server.domain.trade.dto.TradeRequestDto;
import com.keumbang.api.resource_server.domain.trade.service.TradeService;
import com.keumbang.api.resource_server.global.common.exception.CustomException;
import com.keumbang.api.resource_server.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor
@Tag(name = "Trade API", description = "거래 주문 관련 API")
public class TradeController {
    private final TradeService tradeService;

    /**
     * 새로운 주문을 생성하는 엔드포인트
     *
     * @param tradeRequestDto 주문 생성 요청 데이터
     * @throws CustomException 연관 상품을 찾을 수 없는 경우(PRODUCT_NOT_FOUND)
     * @return 생성된 주문의 ID
     */
    @PostMapping
    @Operation(summary = "거래 주문 생성", description = "새로운 거래 주문을 생성합니다.")
    public ResponseEntity<CommonResponse<Long>> createOrder(@Valid @RequestBody TradeRequestDto tradeRequestDto) {
        return ResponseEntity.ok(CommonResponse.ok("주문이 완료되었습니다.", tradeService.createTrade(tradeRequestDto)));
    }
}
