package com.keumbang.api.resource_server.domain.product.controller;

import com.keumbang.api.resource_server.domain.product.dto.ProductRequestDto;
import com.keumbang.api.resource_server.domain.product.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관련 API")
public class ProductController {
    private final ProductService productService;

    /**
     * 새로운 상품을 등록하는 엔드포인트
     *
     * @param productRequestDto 상품 등록 요청 데이터
     * @return 등록된 상품 ID
     */
    @PostMapping
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    public ResponseEntity<CommonResponse<Long>> registerProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(CommonResponse.ok("상품 등록이 완료되었습니다.", productService.registerProduct(productRequestDto)));
    }
}
