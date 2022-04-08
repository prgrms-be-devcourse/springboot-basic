package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Voucher {

  long getDiscountDegree();

  UUID getVoucherId();

  long discount(long beforeDiscount);


}
