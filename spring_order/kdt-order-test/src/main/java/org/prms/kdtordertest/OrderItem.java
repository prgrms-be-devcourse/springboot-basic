package org.prms.kdtordertest;

import java.util.UUID;

// record라고 변경되면 간결하게 사용 가능 (생성자, get, set없이)
public record OrderItem(UUID productId,
        long productPrice,
        long quantity) {

}
