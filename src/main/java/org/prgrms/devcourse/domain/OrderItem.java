package org.prgrms.devcourse.domain;

import java.util.UUID;

public record OrderItem(UUID productId,
                        long productPrice,
                        long quantity) {
}
