package org.prgrms.springbootbasic.console;

import org.prgrms.springbootbasic.voucher.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ConsoleOutput implements Output {
  public static final String AVAILABLE_COMMANDS = "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.\n";
  public static final String AVAILABLE_VOUCHERS = "Which type do you want, FixedAmountVoucher(Fixed) or PercentDiscountVoucher(Percent)?";
  public static final String INPUT = "Input: ";
  public static final String VOUCHER_TYPE = "Input Voucher Type: ";

  @Override
  public void printVoucherList(Map<UUID, Voucher> vouchers) {
    for(UUID voucherId: vouchers.keySet()) {
      System.out.println(vouchers.get(voucherId));
    }
  }

  @Override
  public void printAvailableCommandList() {
    System.out.println(AVAILABLE_COMMANDS);
  }

  @Override
  public void printAvailableCreateCommandList() {
    System.out.println(AVAILABLE_VOUCHERS);
  }

  public void printInputMessage() {
    System.out.print(INPUT);
  }

  @Override
  public void printVoucherTypeMessage() {
    System.out.print(VOUCHER_TYPE);
  }
}
