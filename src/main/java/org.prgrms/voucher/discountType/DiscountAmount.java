package org.prgrms.voucher.discountType;

public class DiscountAmount implements Amount {

  private final long value;

  public DiscountAmount(String input) {

    this.value = getTarget(input);
  }

  public long getValue() {
    return value;
  }

  private long getTarget(String input) {
    long target;
    try {
      target = Long.parseLong(input);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Only numbers can be entered");
    }
    if (target < 1) {
      throw new IllegalStateException("Wrong discount amount range");
    }
    return target;
  }

}
