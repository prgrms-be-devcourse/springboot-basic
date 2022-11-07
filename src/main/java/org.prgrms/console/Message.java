package org.prgrms.console;


public class Message {

  public final String SHOW_SUPPORTED_COMMANDS() {
    return """
        === Voucher Program ===
        Type exit to exit the program.
        Type create to create a new voucher.
        Type list to list all vouchers.""";
  }

  public final String CHOOSE_VOUCHER_TYPE() {
    return """
        === Please select a voucher type(numbers only) ===
        1.FixedAmountVoucher
        2.PercentDiscountVoucher
        """;
  }

  public final String ENTER_DISCOUNT_RATE() {
    return "=== Please enter discount rate(numbers only) ===";
  }

  public final String ENTER_DISCOUNT_AMOUNT() {
    return "=== Please enter the discount amount(numbers only) ===";
  }

}
