package com.keumbang.api.resource_server.domain.trade.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradeStatus {
    // 구매 주문 상태
    PURCHASE_TRADE_COMPLETED("구매 주문 완료"),
    PAYMENT_COMPLETED("입금 완료"),
    SHIPPED("발송 완료"),

    // 판매 주문 상태
    SALES_TRADE_COMPLETED("판매 주문 완료"),
    REMITTANCE_COMPLETED("송금 완료"),
    RECEIPT_COMPLETED("수령 완료");

    private final String description;

    public static TradeStatus createOrderOf(TradeType type) {
        return type == TradeType.PURCHASE ? PURCHASE_TRADE_COMPLETED : SALES_TRADE_COMPLETED;
    }

}
