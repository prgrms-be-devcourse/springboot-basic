package org.prgrms.vouchermanagement.console;

import org.prgrms.vouchermanagement.console.reader.Reader;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConsoleInput implements Input {

  private final Output printer;
  private final Reader reader;

  public ConsoleInput(Output output, Reader reader) {
    this.printer = output;
    this.reader = reader;
  }

  @Override
  public Optional<String> read() {
    String line = reader.read();
    if(line.length() == 0) {
      return Optional.empty();
    }
    return Optional.of(line);
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