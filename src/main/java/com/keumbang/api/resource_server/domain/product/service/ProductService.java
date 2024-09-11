package com.keumbang.api.resource_server.domain.product.service;

import com.keumbang.api.resource_server.domain.product.dto.ProductRequestDto;
import com.keumbang.api.resource_server.domain.product.entity.Product;
import com.keumbang.api.resource_server.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long registerProduct(ProductRequestDto productRequestDto) {
        Product product = Product.builder()
                .type(productRequestDto.getType())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .build();
        productRepository.save(product); // 상품 저장 및 반환
        return product.getId();
    }
}
