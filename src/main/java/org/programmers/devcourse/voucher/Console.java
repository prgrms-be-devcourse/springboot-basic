package org.programmers.devcourse.voucher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.MenuSelection;
import org.programmers.devcourse.voucher.engine.blacklist.BlackList;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.VoucherMapper;
import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output {


  private static final String MENU_STRING = "\n=== Voucher Program ===\n"
      + "Type exit to exit the program.\n"
      + "Type create to create a new voucher.\n"
      + "Type list to list all vouchers.\n"
      + "Type blacklist to check blacklist";
  private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

  private Console() {
  }

  @Override
  public Optional<MenuSelection> getSelection() throws IOException {
    System.out.println(MENU_STRING);
    System.out.print(">> ");

    return MenuSelection.from(consoleReader.readLine());
  }

  @Override
  public VoucherMapper getVoucherMapper() throws IOException {
    Optional<VoucherMapper> voucherMapper;
    while (true) {
      System.out.println("== Create ==");
      System.out.println("Select type of voucher");
      System.out.println("FixedAmountVoucher --> type 1");
      System.out.println("PercentDiscountVoucher --> type 2");
      voucherMapper = VoucherMapper.from(consoleReader.readLine());
      if (voucherMapper.isPresent()) {
        return voucherMapper.get();
      }
      System.out.println("Wrong input, please try again");
    }
  }

  @Override
  public long getVoucherDiscountData(VoucherMapper voucherMapper) throws IOException {
    long discountData = Long.MIN_VALUE;
    while (discountData == Long.MIN_VALUE) {
      System.out.print(
          MessageFormat.format("Type amount of discount(unit: {0}) >> ", voucherMapper.getUnit()));
      try {
        discountData = Long.parseLong(consoleReader.readLine());
        if (discountData <= 0) {
          throw new NumberFormatException();
        }

      } catch (NumberFormatException e) {
        System.out.println("Only positive integer accepted");
      }
    }
    return discountData;
  }

  @Override
  public void print(String data) {
    System.out.println(data);
  }

  @Override
  public void printAllVouchers(Map<UUID, Voucher> voucherMap) {
    if (voucherMap.isEmpty()) {
      System.out.println("EMPTY!");
      return;
    }
    var voucherString = new StringBuilder();
    voucherString.append("=== VoucherList ===\n");
    voucherMap.values().forEach(voucher -> voucherString.append(voucher).append("\n"));

    System.out.println(voucherString);
  }

  @Override
  public void printBlackList(List<BlackList> list) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("\n");
    buffer.append("=========BLACKLIST==========\n");
    if (list.isEmpty()) {
      buffer.append("EMPTY\n");
    } else {
      list.forEach(blackList -> buffer.append(blackList.toString()).append("\n"));

    }
    buffer.append("============================\n");
    System.out.println(buffer.toString());

  }


  @Override
  public void close() throws IOException {
    consoleReader.close();
  }
}
