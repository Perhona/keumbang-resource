package com.keumbang.api.resource_server.domain.product.dto;

import com.keumbang.api.resource_server.domain.product.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "상품 등록 요청 DTO")
public class ProductRequestDto {
    @NotNull(message = "상품 타입은 필수 항목입니다.")
    @Schema(description = "상품 타입", example = "GOLD_9999")
    private ProductType type;

    @Schema(description = "상품 설명", example = "순도 99.99% 금입니다. 최고 인기 상품입니다!")
    private String description;

    @NotNull(message = "그램당 가격은 필수 항목입니다.")
    @Schema(description = "그램(g)당 가격", example = "100000")
    private Integer price; // 그램당 가격
}
