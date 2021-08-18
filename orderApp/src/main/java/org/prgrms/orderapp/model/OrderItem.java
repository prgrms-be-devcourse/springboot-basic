package org.prgrms.orderapp.model;

import java.util.UUID;

public record OrderItem(
        UUID productId,
        long productPrice,
        long quantity
) {}