package com.mehmetozanguven.outbox_pattern_with_spring_boot.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/order")
    public String createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws JsonProcessingException {
        return orderService.createOrder(createOrderRequest);
    }

    @PostMapping("/api/order-with-outbox")
    public String createOrderWithOutbox(@RequestBody CreateOrderRequest createOrderRequest) throws JsonProcessingException {
        return orderService.createOrderWithOutbox(createOrderRequest);
    }
}
