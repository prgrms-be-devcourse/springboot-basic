package org.prgrms.kdt.order;

import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 9:12 오후
 */
public record OrderItem(UUID productId, long productPrice, long quantity) {

}
