package programmers.org.kdt.engine.order;

import java.util.UUID;

public record OrderItem(
        UUID productId,
        long productPrice,
        long quantity)
{

}
