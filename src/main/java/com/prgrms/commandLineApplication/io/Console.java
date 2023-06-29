package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

  private static final Scanner scanner = new Scanner(System.in);

  public static final String PRINT_MENU = """
          === Voucher Program ===
          Type exit to exit the program.
          Type create to create a new voucher.
          Type list to list all vouchers.
          """;

  public String userInput() {
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
    System.out.println("- fixed \n- percent");
    System.out.println("Enter the type you want to create");
  }

  @Override
  public void requestDiscountAmount() {
    System.out.println("Enter the value you want to create");
  }

  @Override
  public void printError() {
    System.out.println("Select from the menu in the view");
  }

  @Override
  public void printAllVoucher(List<Voucher> vouchers) {
    for (Voucher voucher : vouchers) {
      System.out.println("UUID : " + voucher.getVoucherId() +
              "\nVoucher : " + voucher.getDiscount().getVoucherType() + " , " + voucher.supplyDiscountAmount());
    }
  }

  @Override
  public void printCreateSuccess() {
    System.out.println("Successfully created");
  }

}
