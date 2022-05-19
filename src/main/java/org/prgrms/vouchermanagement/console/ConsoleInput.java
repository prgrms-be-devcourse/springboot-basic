package org.prgrms.vouchermanagement.console;

import org.prgrms.vouchermanagement.console.reader.Reader;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput implements Input {

  private final Output printer;
  private final Reader reader;

  public ConsoleInput(Output output, Reader reader) {
    this.printer = output;
    this.reader = reader;
  }

  @Override
  public String readCommand() {
    printer.printCommandList();
    return reader.read();
  }

  @Override
  public String readListType() {
    printer.printListType();
    return reader.read();
  }
}