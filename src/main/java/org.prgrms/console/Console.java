package org.prgrms.console;

import java.util.List;
import java.util.Scanner;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class Console {

  private final Scanner scanner = new Scanner(System.in);

  private final Message message;

  public Console() {
    this.message = new Message();
  }

  public String chooseMenu() {
    printSupportedCommands();
    return convertInputToLowercase();
  }

  private String convertInputToLowercase() {
    return scanner.nextLine().toLowerCase().trim();
  }

  private void printSupportedCommands() {
    System.out.println(message.SHOW_SUPPORTED_COMMANDS());
  }

  public String chooseVoucherType() {
    System.out.println(message.CHOOSE_VOUCHER_TYPE());
    return scanner.nextLine();
  }

  public String enterDiscountRate(VoucherType voucherType) {
    switch (voucherType) {
      case FIXED -> System.out.println(message.ENTER_DISCOUNT_AMOUNT());
      case PERCENT -> System.out.println(message.ENTER_DISCOUNT_RATE());
    }
    return scanner.nextLine();
  }

  public void printSavedVoucher(Voucher voucher) {
    System.out.println(voucher.toString());
  }

  public void printVoucherList(List<Voucher> voucherList) {
    voucherList.forEach(System.out::println);
  }

  public void printErrorMsg(String error) {
    System.out.println(error);
  }
}
