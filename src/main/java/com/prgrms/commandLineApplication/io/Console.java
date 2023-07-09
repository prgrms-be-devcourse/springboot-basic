package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.discount.DiscountType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

  private static final Logger logger = LoggerFactory.getLogger(Console.class);
  private static final Scanner scanner = new Scanner(System.in);

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
    System.out.println("FIXED" + " | " + "PERCENT\n" + ENTER_CREATE_TYPE);
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
      DiscountType discountType = voucher.supplyDiscountType();
      System.out.println(String.format("- VOUCHER_ID : %s", voucher.getVoucherId()));
      System.out.println(String.format("- %s VOUCHER : %s%s", discountType, voucher.supplyDiscountAmount(), DiscountType.getUnit(discountType)));
    }
  }

  @Override
  public void printCreateSuccess(String voucherType, int discountAmount) {
    logger.info("{} : VOUCHER => {} {} ", CREATE_SUCCESS, voucherType.toUpperCase(), discountAmount);
  }

  @Override
  public void printErrorMessage(Exception e) {
    System.out.println(e.getMessage());
  }

}
