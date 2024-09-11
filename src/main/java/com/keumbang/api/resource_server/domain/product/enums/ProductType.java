package com.keumbang.api.resource_server.domain.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    GOLD_9999("G9999", "99.99% 금"),
    GOLD_999("G9990", "99.9% 금")
    ;

    private final String code;      // 주문번호에 넣을 코드
    private final String description; // 설명 (디스플레이용)

}
