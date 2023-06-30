package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

  private static final Scanner scanner = new Scanner(System.in);

  public static final String FIXED = "- fixed ";
  public static final String PERCENT = "- percent";
  public static final String ENTER_CREATE_TYPE = "Enter the type you want to create";
  public static final String ENTER_CREATE_VALUE = "Enter the value you want to create";
  public static final String SELECT_MENU_ERROR = "Select from the menu in the view";
  public static final String UUID = "- UUID : ";
  public static final String VOUCHER = "- VOUCHER : ";
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
  public void printMenu() {
    System.out.println(PRINT_MENU);
  }

  @Override
  public void requestVoucherType() {
    System.out.println(FIXED + PERCENT);
    System.out.println(ENTER_CREATE_TYPE);
  }

  @Override
  public void requestDiscountAmount() {
    System.out.println(ENTER_CREATE_VALUE);
  }

  @Override
  public void printMenuError() {
    System.out.println(SELECT_MENU_ERROR);
  }

  @Override
  public void printAllVoucher(List<Voucher> vouchers) {
    for (Voucher voucher : vouchers) {
      System.out.println(UUID + voucher.getVoucherId());
      System.out.println(VOUCHER + voucher.getDiscount().getVoucherType() + " , " + voucher.supplyDiscountAmount());
    }
  }

  @Override
  public void printCreateSuccess() {
    System.out.println(CREATE_SUCCESS);
  }

}
