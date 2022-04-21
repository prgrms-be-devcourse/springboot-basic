package org.prgrms.springbootbasic.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
  private final Output output;
  private static final Scanner scanner = new Scanner(System.in);

  public ConsoleInput(Output output) {
    this.output = output;
  }

  @Override
  public String readCommand() {
    output.printInputMessage();
    String command = scanner.nextLine().trim();
    return command;
  }

  @Override
  public String readVoucherType() {
    output.printVoucherTypeMessage();
    String voucherType = scanner.nextLine().trim();
    return voucherType;
  }
}
