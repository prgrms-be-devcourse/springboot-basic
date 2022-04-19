package org.programmers.devcourse.voucher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import org.programmers.devcourse.voucher.engine.MenuSelection;
import org.programmers.devcourse.voucher.engine.blacklist.BlackList;
import org.programmers.devcourse.voucher.engine.exception.NoSuchOptionException;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
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
  public MenuSelection getSelection() throws IOException {
    System.out.println(MENU_STRING);
    System.out.print(">> ");

    return MenuSelection.from(consoleReader.readLine()).orElseThrow(NoSuchOptionException::new);
  }

  @Override
  public String getVoucherTypeId() throws IOException {
    System.out.println("== Create ==");
    System.out.println("Select type of voucher");
    for (VoucherType voucherType : VoucherType.values()) {
      System.out.printf("%s --> type %d%n", voucherType.name(), voucherType.ordinal() + 1);
    }

    return consoleReader.readLine();
  }

  @Override
  public long getVoucherDiscountData(VoucherType voucherType) throws IOException {
    long discountData = Long.MIN_VALUE;
    while (discountData == Long.MIN_VALUE) {
      System.out.print(
          MessageFormat.format("Type amount of discount(unit: {0}) >> ", voucherType.getUnit()));
      try {
        discountData = Long.parseLong(consoleReader.readLine());
      } catch (NumberFormatException ignored) {
        System.out.println("정수를 입력해 주세요!");
      }
    }
    return discountData;
  }

  @Override
  public void printInputError(String message) {

    System.out.println(MessageFormat.format("[ERROR] : {0}", message));

  }

  @Override
  public void print(String data) {
    System.out.println(data);
  }

  @Override
  public void printAllVouchers(Collection<Voucher> vouchers) {
    if (vouchers.isEmpty()) {
      System.out.println("EMPTY!");
      return;
    }
    var voucherString = new StringBuilder();
    voucherString.append("=== VoucherList ===\n");
    vouchers.forEach(voucher -> voucherString.append(voucher).append("\n"));

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
    System.out.println(buffer);

  }


  @Override
  public void close() throws IOException {
    consoleReader.close();
  }
}
