package com.prgrms.model.order;

public record OrderItem(
        int productId,
        Price productPrice,
        long quantity
) { }
