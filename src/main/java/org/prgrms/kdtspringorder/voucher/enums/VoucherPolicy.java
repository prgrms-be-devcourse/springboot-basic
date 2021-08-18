package org.prgrms.kdtspringorder.voucher.enums;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherPolicy {

  FIXED("fixed", (originalPrice) -> originalPrice - 10000),
  PERCENT("percent", (originalPrice) -> ((originalPrice * 100) - (originalPrice * 30)) / 100);

  private final Function<Long, Long> discounter;
  private final String TYPE;

  VoucherPolicy(String type, Function<Long, Long> discountPolicy) {
    this.TYPE = type;
    this.discounter = discountPolicy;
  }

  public long discount(long originalPrice) {
    return discounter.apply(originalPrice);
  }

  public String getPolicyType() {
    return this.TYPE;
  }

  public static VoucherPolicy of(String policy) {
    return Arrays.stream(VoucherPolicy.values())
        .filter(voucherPolicy -> voucherPolicy.getPolicyType().equals(policy)).findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(policy + " : 해당 타입의 바우처는 존재하지 않습니다."));
  }
}
