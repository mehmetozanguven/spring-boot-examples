package com.mehmetozanguven.outbox_pattern_with_spring_boot.order;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateOrderRequest{
    private String productName;
}
