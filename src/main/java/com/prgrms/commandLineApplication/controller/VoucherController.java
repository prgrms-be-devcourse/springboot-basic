package com.prgrms.commandLineApplication.controller;

import com.prgrms.commandLineApplication.execute.CustomerExecution;
import com.prgrms.commandLineApplication.execute.VoucherExecution;
import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.io.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;

@Controller
public class VoucherController {

  private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
  private final VoucherExecution voucherExecution;
  private final CustomerExecution customerExecution;
  private final Console console;

  public VoucherController(VoucherExecution voucherExecution, CustomerExecution customerExecution, Console console) {
    this.voucherExecution = voucherExecution;
    this.customerExecution = customerExecution;
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
          case VOUCHER_LIST -> voucherExecution.printList();
          case CREATE_VOUCHER -> voucherExecution.create();
          case CUSTOMER_LIST -> customerExecution.printList();
          case CREATE_CUSTOMER -> customerExecution.create();
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
