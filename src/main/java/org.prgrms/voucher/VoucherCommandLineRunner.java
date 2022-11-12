package org.prgrms.voucher;

import static org.prgrms.voucher.MenuType.CREATE;
import static org.prgrms.voucher.MenuType.EXIT;

import java.util.List;
import org.prgrms.console.Console;
import org.prgrms.exception.NoSuchMenuTypeException;
import org.prgrms.voucher.discountType.Amount;

import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherTypePool;
import org.prgrms.voucherMemory.VoucherMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherCommandLineRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(VoucherCommandLineRunner.class);

  private final Console console;

  private final VoucherMemory voucherMemory;

  public VoucherCommandLineRunner(Console console, VoucherMemory voucherMemory) {
    this.console = console;
    this.voucherMemory = voucherMemory;
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

  private MenuType execute() {
    MenuType menu = MenuType.of(console.chooseMenu());
    logger.info("Menu : {}", menu);

    switch (menu) {
      case EXIT -> {
        return EXIT;
      }

      case CREATE -> {
        Voucher savedVoucher = createVoucher();
        console.printSavedVoucher(savedVoucher);
        logger.info("saved_voucher: {}", savedVoucher);
        return CREATE;
      }

      case LIST -> {
        List<Voucher> voucherList = voucherMemory.findAll();
        console.printVoucherList(voucherList);
        logger.info("voucherList: {}", voucherList);
        return MenuType.LIST;
      }

      default ->
          throw new NoSuchMenuTypeException("The command could not be found. Please re-enter");
    }
  }

  public Voucher createVoucher() {

    while (true) {
      try {
        VoucherTypePool voucherType = enteredVoucherType();
        String inputAmount = console.enteredAmount(voucherType);
        logger.info("input_amount: {}", inputAmount);

        Amount amount = voucherType.generateAmount(inputAmount);

        return voucherMemory.save(voucherType.generateVoucher(amount));
      } catch (RuntimeException e) {
        console.printErrorMsg(e.getMessage());
        logger.warn("class: {}, message: {}", e.getClass().getName(), e.getMessage());
      }
    }
  }

  private VoucherTypePool enteredVoucherType() {
    String inputType = console.chooseVoucherType();
    return VoucherTypePool.of(inputType);
  }

  private boolean isExit(MenuType menuType) {
    return menuType == EXIT;
  }

}
