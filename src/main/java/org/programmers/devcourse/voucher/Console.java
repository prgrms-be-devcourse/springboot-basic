package org.programmers.devcourse.voucher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Optional;
import org.programmers.devcourse.voucher.engine.MenuSelection;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.Voucher.Type;
import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output {

  private static final String MENU_STRING = "=== Voucher Program ===\n"
      + "Type exit to exit the program.\n"
      + "Type create to create a new voucher.\n"
      + "Type list to list all vouchers.";
  private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  private Console() {
  }

  @Override
  public Optional<MenuSelection> getSelection() throws IOException {
    System.out.println(MENU_STRING);
    System.out.print(">> ");

    return MenuSelection.from(br.readLine());
  }

  @Override
  public Type getVoucherType() throws IOException {
    Optional<Type> voucherType;
    while (true) {
      System.out.println("== Create ==");
      System.out.println("Select type of voucher");
      System.out.println("FixedAmountVoucher --> type 1");
      System.out.println("PercentDiscountVoucher --> type 2");
      voucherType = Voucher.Type.from(br.readLine());
      if (voucherType.isPresent()) {
        return voucherType.get();
      }
      System.out.println("Wrong input, please try again");
    }
  }

  @Override
  public long getVoucherDiscountData(Type voucherType) throws IOException {
    long discountData = Long.MIN_VALUE;
    while (discountData == Long.MIN_VALUE) {
      System.out.print(
          MessageFormat.format("Type amount of discount(unit: {0}) >> ", voucherType.getUnit()));
      try {
        discountData = Long.parseLong(br.readLine());
        if (discountData <= 0) {
          throw new NumberFormatException();
        }

      } catch (NumberFormatException e) {
        System.out.println("Only positive long accepted");
      }
    }
    return discountData;
  }

  @Override
  public void print(String data) {
    System.out.println(data);
  }


  @Override
  public void close() throws IOException {
    br.close();
  }
}
