package com.prgrms.order.model;

public record OrderItem(
        String productId,
        Price productPrice,
        long quantity
) { }
