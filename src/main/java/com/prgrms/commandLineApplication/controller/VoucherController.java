package com.prgrms.commandLineApplication.controller;

import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.io.MenuType;
import com.prgrms.commandLineApplication.repository.MemoryVoucherRepository;
import com.prgrms.commandLineApplication.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

  VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());
  Console console = new Console();

  public void run() {
    while (true) {
      try {
        console.printMenu();

        switch (MenuType.valueOfType(console.readMenu())) {
          case EXIT:
            return;
          case LIST:
            console.printVoucherInformation();
            break;
          case CREATE:
            console.requestVoucherType();
            String voucherType = console.readVoucherType();
            console.requestDiscountAmount();
            int disocuntAmount = console.readVoucherAmount();
            voucherService.create(voucherType, disocuntAmount);
            console.printCreateSuccess();
            break;
          default:
            console.printError();
        }
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
      }
    }
  }

}
