package org.programmers.devcourse.voucher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import org.programmers.devcourse.voucher.engine.Selection;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output {

  private static final String MENU_STRING = "=== Voucher Program ===\n"
      + "Type exit to exit the program.\n"
      + "Type create to create a new voucher.\n"
      + "Type list to list all vouchers.";
  private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  @Override
  public Optional<Selection> getSelection() throws IOException {
    System.out.println(MENU_STRING);
    System.out.print(">> ");

    return Selection.from(br.readLine());
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
