package org.prgrms.console;

import java.util.List;
import java.util.Scanner;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class Console {

  private final static String SHOW_SUPPORTED_COMMANDS = """
      === Voucher Program ===
      Type exit to exit the program.
      Type create to create a new voucher.
      Type list to list all vouchers.
      Type black to search the customer blacklist""";
  private final static String CHOOSE_VOUCHER_TYPE = """
      === Please select a voucher type(numbers only) ===
      1.FixedAmountVoucher
      2.PercentDiscountVoucher
      """;
  private final static String ENTER_DISCOUNT_RATE = "=== Please enter discount rate(numbers only) ===";
  private final static String ENTER_DISCOUNT_AMOUNT = "=== Please enter the discount amount(numbers only) ===";
  private final Scanner scanner = new Scanner(System.in);


  public String chooseMenu() {
    System.out.println(SHOW_SUPPORTED_COMMANDS);
    return enteredSelectedMenu()
        .toLowerCase()
        .trim();
  }

  private String enteredSelectedMenu() {
    return scanner.nextLine();
  }

  public String chooseVoucherType() {
    System.out.println(CHOOSE_VOUCHER_TYPE);
    return scanner.nextLine();
  }

  public String enteredAmount(VoucherType voucherType) {
    switch (voucherType) {
      case FIXED -> System.out.println(ENTER_DISCOUNT_AMOUNT);
      case PERCENT -> System.out.println(ENTER_DISCOUNT_RATE);
      default -> throw new NoSuchVoucherTypeException(voucherType.getType());
    }
    return scanner.nextLine();
  }

  public void printSavedVoucher(Voucher voucher) {
    System.out.println(voucher);
  }

  public void printVoucherList(List<Voucher> voucherList) {
    voucherList.forEach(System.out::println);
  }

  public void printErrorMsg(String error) {
    System.out.println(error);
  }

  public void printBlackList(List<String> blacklist) {
    blacklist.forEach(System.out::println);
  }
}
