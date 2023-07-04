package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.discount.DiscountType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

  private static final Scanner scanner = new Scanner(System.in);

  public static final String FIXED = "FIXED";
  public static final String PERCENT = "PERCENT";
  public static final String UUID = "UUID";
  public static final String VOUCHER = "VOUCHER";
  public static final String UNIT_PERCENT = "%";
  public static final String UNIT_WON = "₩";

  public static final String ENTER_CREATE_TYPE = "Enter the type you want to create";
  public static final String ENTER_CREATE_VALUE = "Enter the value you want to create";
  public static final String SELECT_MENU_ERROR = "Select from the menu in the view";
  public static final String CREATE_SUCCESS = "Successfully created";
  public static final String PRINT_MENU = """
          === Voucher Program ===
          Type exit to exit the program.
          Type create to create a new voucher.
          Type list to list all vouchers.
          """;

  private String userInput() {
    return scanner.nextLine();
  }

  @Override
  public String readMenu() {
    return userInput();
  }

  @Override
  public String readVoucherType() {
    return userInput();
  }

  @Override
  public int readVoucherAmount() {
    return Integer.parseInt(userInput());
  }

  @Override
  public void requestVoucherType() {
    System.out.println(String.format("-%s | -%s", FIXED, PERCENT));
    System.out.println(ENTER_CREATE_TYPE);
  }

  @Override
  public void requestDiscountAmount() {
    System.out.println(ENTER_CREATE_VALUE);
  }

  @Override
  public void printMenu() {
    System.out.println(PRINT_MENU);
  }

  @Override
  public void printMenuError() {
    System.out.println(SELECT_MENU_ERROR);
  }

  @Override
  public void printAllVoucher(List<Voucher> vouchers) {
    for (Voucher voucher : vouchers) {
      DiscountType discountType = voucher.getDiscount().getDiscountType();
      System.out.println(String.format("- %s : %s", UUID, voucher.getVoucherId()));
      System.out.println(String.format("- %s %s : %s%s", discountType, VOUCHER, voucher.supplyDiscountAmount(), printUnit(discountType)));
    }
  }

  private String printUnit(DiscountType discountType) {
    return FIXED.equals(discountType.name()) ? UNIT_WON : UNIT_PERCENT;
  }

  @Override
  public void printCreateSuccess() {
    System.out.println(CREATE_SUCCESS);
  }

  @Override
  public void printErrorMessage(Exception e) {
    System.out.println(e.getMessage());
  }

}
