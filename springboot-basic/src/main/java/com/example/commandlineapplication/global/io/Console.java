package com.example.commandlineapplication.global.io;

import com.example.commandlineapplication.domain.voucher.VoucherType;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Console implements Input, Output {

  private static final Scanner scanner = new Scanner(System.in);

  @Override
  public String input() {
    return scanner.nextLine();
  }

  @Override
  public Long getDiscount() {
    return Long.valueOf(scanner.nextLine());
  }

  @Override
  public void printMenu() {
    System.out.println(
        "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType delete to delete a voucher.\nType list to list all vouchers.");
  }

  @Override
  public void printCreateOption() {
    System.out.println(
        "Select voucher type " + VoucherType.PERCENT.getLowerCaseVoucherType() + " or "
            + VoucherType.FIXED.getLowerCaseVoucherType() + ".");
  }

  @Override
  public void printDiscount() {
    System.out.println("Input discount amount.");
  }

  @Override
  public void printDeleteUUID() {
    System.out.println("Input UUID to delete");
  }

  public VoucherType selectVoucherTypeOption() {
    printCreateOption();
    return VoucherType.of(input());
  }

  public Long selectDiscount() {
    printDiscount();
    return getDiscount();
  }
}
