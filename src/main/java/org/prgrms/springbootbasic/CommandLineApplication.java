package org.prgrms.springbootbasic;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLineApplication {
//  static private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public static void main(String[] args) {
    while (true) {
      TextIO textIO = TextIoFactory.getTextIO();
      String input = textIO.newStringInputReader()
        .withDefaultValue("No Input")
        .read("=== Voucher Program ===\n" +
          "Type exit to exit the program.\n" +
          "Type create to create a new voucher.\n" +
          "Type list to list all vouchers.");
      if(input.equals("exit")) break;
    }
  }
}
