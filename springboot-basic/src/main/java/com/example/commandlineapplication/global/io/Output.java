package com.example.commandlineapplication.global.io;

import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class Output {

  public void printMenu() {
    System.out.println(
        "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.");
  }

  public void printCreateOption() {
    System.out.println(
        "Select voucher type " + VoucherType.PERCENT.getLowerCaseVoucherType() + " or "
            + VoucherType.FIXED.getLowerCaseVoucherType() + ".");
  }

  public void printDiscount() {
    System.out.println("Input discount amount.");
  }
}
