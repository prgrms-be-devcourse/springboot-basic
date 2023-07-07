package com.example.commandlineapplication.global.io;

import com.example.commandlineapplication.domain.voucher.dto.response.VoucherResponse;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import com.example.commandlineapplication.domain.voucher.service.VoucherService;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Console implements Input, Output {

  private static final Scanner scanner = new Scanner(System.in);
  private final VoucherService voucherService;

  @Override
  public String selectOption() {
    return scanner.nextLine();
  }

  @Override
  public Integer getDiscount() {
    return Integer.valueOf(scanner.nextLine());
  }

  @Override
  public void printMenu() {
    System.out.println(
        "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.");
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
  public void printHistory() {
    List<VoucherResponse> vouchers = voucherService.findVouchers();

    for (VoucherResponse voucher : vouchers) {
      System.out.println(voucher.getVoucherType() + " " + voucher.getVoucherId().toString() + " "
          + voucher.getDiscountAmount());
    }
  }

  public VoucherType selectVoucherTypeOption() {
    printCreateOption();
    return VoucherType.of(selectOption());
  }

  public Integer selectDiscount() {
    printDiscount();
    return getDiscount();
  }
}
