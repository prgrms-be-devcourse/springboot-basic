package org.prgrms.voucher.discountType;


public class DiscountAmount implements Amount {

  private final long value;

  public DiscountAmount(long input) {
    validateAmount(input);
    this.value = input;
  }

  private void validateAmount(long input) {
    if (input < 1) {
      throw new IllegalStateException("Wrong discount amount range");
    }
  }

  public long getValue() {
    return value;
  }

}
