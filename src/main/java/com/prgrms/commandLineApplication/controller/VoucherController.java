package com.prgrms.commandLineApplication.controller;

import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.io.MenuType;
import com.prgrms.commandLineApplication.service.VoucherService;
import com.prgrms.commandLineApplication.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class VoucherController {

  private static final Logger LOGGER = LoggerFactory.getLogger(VoucherController.class);

  private final VoucherService voucherService;
  private final Console console;

  public VoucherController(VoucherService voucherService, Console console) {
    this.voucherService = voucherService;
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
          case LIST -> list();
          case CREATE -> create();
        }
      } catch (IllegalArgumentException e) {
        LOGGER.error("Error Message => {}", e.getMessage());
        console.printErrorMessage(e);
      } catch (NoSuchElementException e) {
        LOGGER.warn("Warn Message => {}", e.getMessage());
        console.printErrorMessage(e);
      }
    }
  }

  private void list() {
    List<Voucher> list = voucherService.findAllVouchers();
    console.printAllVoucher(list);
  }

  private void create() {
    console.requestVoucherType();
    String voucherType = console.readVoucherType();

    console.requestDiscountAmount();
    int discountAmount = console.readVoucherAmount();

    voucherService.create(voucherType, discountAmount);
    console.printCreateSuccess(voucherType, discountAmount);
  }

}
