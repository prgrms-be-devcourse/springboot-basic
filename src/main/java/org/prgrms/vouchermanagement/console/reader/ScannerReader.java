package org.prgrms.vouchermanagement.console.reader;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerReader implements Reader {
  private final Scanner scanner = new Scanner(System.in);

  @Override
  public String read() {
    return scanner.nextLine();
  }
}
