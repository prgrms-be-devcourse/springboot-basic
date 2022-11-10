package org.prgrms.voucher.discountType;

public class DiscountRate implements Amount {

  private final long value;

  public DiscountRate(String input) {
    long target;
    try {
      target = Long.parseLong(input);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Only numbers can be entered");
    }
    if (target > 100 || target < 1) {
      throw new IllegalStateException("Invalid discount rate range");
    }

    this.value = target;
  }

  public long getValue() {
    return value;
  }
}
