package com.example.commandlineapplication.global.io;

import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import com.example.commandlineapplication.domain.voucher.repository.MemoryVoucherRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Output {

  private final MemoryVoucherRepository memoryVoucherRepository;

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

  public void printHistory() {
    List<Voucher> historyList = memoryVoucherRepository.findAll();
    for (Voucher voucher : historyList) {
      System.out.println(voucher.getVoucherType() + " " + voucher.getVoucherId().toString());
    }
  }
}
