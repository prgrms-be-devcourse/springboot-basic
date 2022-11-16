package org.prgrms.voucher.discountType;

public class DiscountAmount implements Amount {

  private final long value;

  public DiscountAmount(long input) {
    validateAmount(input);
    this.value = input;
  }

  public long getValue() {
    return value;
  }

}
