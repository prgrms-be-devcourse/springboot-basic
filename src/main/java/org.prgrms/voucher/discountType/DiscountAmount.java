package org.prgrms.voucher.discountType;

public class DiscountAmount implements Amount {

  private final long value;

  public DiscountAmount(String input) {

    this.value = getTarget(input);
  }

  public long getValue() {
    return value;
  }

  private static long getTarget(String input) {
    long target;
    try {
      target = Long.parseLong(input);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("숫자만 입력 가능합니다");
    }
    if (target < 1) {
      throw new IllegalStateException("잘못된 할인금액 범위입니다");
    }
    return target;
  }

}
