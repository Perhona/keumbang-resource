package com.keumbang.api.resource_server.domain.trade.dto;

import com.keumbang.api.resource_server.domain.trade.enums.TradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "주문 생성 요청 DTO")
public class TradeRequestDto {
    @NotNull(message = "사용자 ID는 필수 항목입니다.")
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @NotNull(message = "상품 ID는 필수 항목입니다.")
    @Schema(description = "상품 ID", example = "101")
    private Long productId;

    @Digits(integer = 10, fraction = 2)
    @NotNull(message = "주문 수량은 필수 항목입니다.")
    @Schema(description = "주문 수량 (그램 단위), 소수점 이하 2자리까지 가능", example = "250.00")
    private BigDecimal quantity;

    @NotNull(message = "배송 주소는 필수 항목입니다.")
    @Schema(description = "배송 주소", example = "서울시 강남구 테헤란로 123")
    private String shippingAddress;

    @NotNull(message = "주문 유형은 필수 항목입니다.")
    @Schema(description = "주문 유형", example = "PURCHASE")
    private TradeType type;
}
