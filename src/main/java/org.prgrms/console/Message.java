package org.prgrms.console;

public enum Message {
  SHOW_SUPPORTED_COMMANDS("""
        === Voucher Program ===
        Type exit to exit the program.
        Type create to create a new voucher.
        Type list to list all vouchers."""),
  CHOOSE_VOUCHER_TYPE("""
        === Please select a voucher type(numbers only) ===
        1.FixedAmountVoucher
        2.PercentDiscountVoucher
        """),
  ENTER_DISCOUNT_RATE("=== Please enter discount rate(numbers only) ==="),
  ENTER_DISCOUNT_AMOUNT("=== Please enter the discount amount(numbers only) ===");

  public final String value;

  Message(String value) {
    this.value = value;
  }
}
