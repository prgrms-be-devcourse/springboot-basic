package com.prgrms.commandLineApplication.controller;

import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

  public static final String EXIT = "exit";
  public static final String LIST = "list";
  public static final String CREATE = "create";

  private final VoucherService voucherService;

  Console console = new Console();

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  public void run() {
    while (true) {
      console.printMenu();

      switch (console.readMenu()) {
        case EXIT:
          return;
        case LIST:
          console.printVoucherInformation();
          break;
        case CREATE:
          console.requestVoucherType();
          String voucherType = console.readVoucherType();
          console.requestDiscountAmount();
          int discountAmount = console.readVoucherAmount();
          voucherService.create(voucherType, discountAmount);
          console.printCreateSuccess();
          break;
        default:
          console.printError();
      }
    }
  }

}
