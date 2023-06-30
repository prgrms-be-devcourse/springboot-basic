package com.prgrms.commandLineApplication.controller;

import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.io.MenuType;
import com.prgrms.commandLineApplication.service.VoucherService;
import com.prgrms.commandLineApplication.voucher.Voucher;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

  private final VoucherService voucherService;
  private final Console console;

  public VoucherController(VoucherService voucherService, Console console) {
    this.voucherService = voucherService;
    this.console = console;
  }

  public void run() {
    while (true) {
      try {
        console.printMenu();
        MenuType menuType = MenuType.valueOfType(console.readMenu());

        switch (menuType) {
          case EXIT -> {
            return;
          }
          case LIST -> list();
          case CREATE -> create();
          default -> console.printMenuError();
        }
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
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
    console.printCreateSuccess();
  }

}
