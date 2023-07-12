package com.prgrms.commandLineApplication.controller;

import com.prgrms.commandLineApplication.execute.CustomerExecute;
import com.prgrms.commandLineApplication.execute.VoucherExecute;
import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.io.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;

@Controller
public class VoucherController {

  private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
  private final VoucherExecute voucherExecute;
  private final CustomerExecute customerExecute;
  private final Console console;

  public VoucherController(VoucherExecute voucherExecute, CustomerExecute customerExecute, Console console) {
    this.voucherExecute = voucherExecute;
    this.customerExecute = customerExecute;
    this.console = console;
  }

  public void run() {
    boolean isRunning = true;

    while (isRunning) {
      try {
        console.printMenu();

        String enterMenu = console.readMenu();
        MenuType menuType = MenuType.valueOfType(enterMenu);

        switch (menuType) {
          case EXIT -> isRunning = false;
          case VOUCHER_LIST -> voucherExecute.printList();
          case CREATE_VOUCHER -> voucherExecute.create();
          case CUSTOMER_LIST -> customerExecute.printList();
          case CREATE_CUSTOMER -> customerExecute.create();
        }
      } catch (IllegalArgumentException e) {
        logger.error("Error Message => {}", e.getMessage(), e);
        console.printErrorMessage(e);
      } catch (NoSuchElementException e) {
        logger.warn("Warn Message => {}", e.getMessage(), e);
        console.printErrorMessage(e);
      }
    }
  }

}
