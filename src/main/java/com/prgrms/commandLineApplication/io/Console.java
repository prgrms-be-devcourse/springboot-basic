package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.repository.MemoryVoucherRepository;
import com.prgrms.commandLineApplication.service.VoucherService;

import java.util.Scanner;

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
    return scanner.nextLine();
  }

  @Override
  public String readVoucherType() {
    return scanner.nextLine();
  }

  @Override
  public int readVoucherAmount() {
    return Integer.parseInt(scanner.nextLine());
  }

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
  public void printVoucherInformation() {
    VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());
    voucherService.findAllVouchers().forEach(voucher ->
            System.out.println("UUID : " + voucher.getVoucherId() +
                    "\nVoucher : " + voucher.getVoucherType() + " , " + voucher.getDiscountAmount()));
  }

  @Override
  public void printCreateSuccess() {
    System.out.println("Successfully created");
  }

}
