package com.prgrms.order.model;

public record OrderItem(
        int productId,
        Price productPrice,
        long quantity
) { }
