package org.prgrms.voucher.discountType;

public class DiscountRate implements Amount {

  private final long value;

  public DiscountRate(long input) {
    validateAmount(input);
    this.value = input;
  }

  private void validateAmount(long input) {
    if (input > 100 || input < 1) {
      throw new IllegalStateException("Wrong discount amount range");
    }
  }

  public long getValue() {
    return value;
  }
}
