package com.prgrms.model.order;

import java.util.UUID;

public record OrderItem(UUID productId,
                        long productPrice,
                        long quantity) {

}
