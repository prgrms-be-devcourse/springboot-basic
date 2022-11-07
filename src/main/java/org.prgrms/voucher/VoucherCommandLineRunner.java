package org.prgrms.voucher;

import org.prgrms.console.Console;
import org.prgrms.exception.NoSuchMenuTypeException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherCommandLineRunner implements CommandLineRunner {

  private final Console console;

  private final VoucherProcessManager voucherProgram;

  public VoucherCommandLineRunner(Console console, VoucherProcessManager voucherProgram) {
    this.console = console;
    this.voucherProgram = voucherProgram;
  }

  @Override
  public void run(String... args) {

    while (VoucherExecution.isRunnable()) {
      try {
        execute();
      } catch (RuntimeException e) {
        console.printErrorMsg(e.getMessage());
      }
    }
  }

  private void execute() {
    MenuType menu = MenuType.of(console.chooseMenu());

    switch (menu) {
      case EXIT -> voucherProgram.exit();

      case CREATE -> voucherProgram.createVoucher();

      case LIST -> voucherProgram.showVoucherList();

      default ->
          throw new NoSuchMenuTypeException("The command could not be found. Please re-enter");
    }
  }

}
