package com.keumbang.api.resource_server.domain.trade.entity;

import com.keumbang.api.resource_server.domain.product.entity.Product;
import com.keumbang.api.resource_server.domain.trade.enums.TradeStatus;
import com.keumbang.api.resource_server.domain.trade.enums.TradeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Digits(integer = 10, fraction = 2) // 정수부 최대 10자리, 소수부 최대 2자리
    @Column(name = "quantity", precision = 12, scale = 2) // 최대 12자리, 소수점 이하 2자리
    private BigDecimal quantity; // 수량

    @Column(name = "cost")
    private Long cost; // 최종 구매 가격

    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;


    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type")
    private TradeType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TradeStatus status;

    @Column(name = "trade_number", length = 30)
    private String tradeNumber;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
