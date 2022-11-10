package org.prgrms.voucher;

import org.prgrms.console.Console;
import org.prgrms.exception.NoSuchMenuTypeException;
import org.prgrms.voucher.discountType.Amount;

import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.prgrms.voucherMemory.VoucherMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherCommandLineRunner implements CommandLineRunner {

  Logger logger = LoggerFactory.getLogger(VoucherCommandLineRunner.class);

  private final Console console;

  public VoucherCommandLineRunner(Console console, VoucherProcessManager voucherProgram) {
    this.console = console;
    this.voucherProgram = voucherProgram;
  }

  @Override
  public void run(String... args) {
    VoucherProgramStatus voucherProgramStatus = new VoucherProgramStatus();

    while (voucherProgramStatus.isRunnable()) {
      try {

        if (isExit(execute())) {
          voucherProgramStatus.stop();
        }

      } catch (RuntimeException e) {
        console.printErrorMsg(e.getMessage());
        logger.warn("class: {}, message: {}", e.getClass().getName(), e.getMessage());
      }
    }
  }

  private void execute() {
    MenuType menu = MenuType.of(console.chooseMenu());
    logger.info("Menu : {}", menu);

    switch (menu) {
      case EXIT -> voucherProgram.exit();

      case CREATE -> voucherProgram.createVoucher();

      case LIST -> voucherProgram.showVoucherList();

      default ->
          throw new NoSuchMenuTypeException("The command could not be found. Please re-enter");
    }
  }

}
