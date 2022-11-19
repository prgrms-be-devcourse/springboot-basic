package org.prgrms.voucher.discountType;

public class DiscountRate implements Amount {

  private final long value;

  public DiscountRate(long input) {
    validateAmount(input);
    this.value = input;
  }

  private void validateAmount(long input) {
    if (input > 100 || input < 1) {
      throw new IllegalStateException(
          "The discount rate is only available between %1 and 100%  *current Amount: " + input);
    }
  }

  public long getValue() {
    return value;
  }
}
