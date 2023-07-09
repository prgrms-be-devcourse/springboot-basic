package com.prgrms.model.order;

public record OrderItem(
        int productId,
        long productPrice,
        long quantity
) { }
