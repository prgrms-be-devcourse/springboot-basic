package org.voucherProject.voucherProject.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OrderItem {

    private final UUID itemId;
    private final long itemPrice;
    private final long quantity;

}
