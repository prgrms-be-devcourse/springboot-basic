package com.example.commandlineapplication.domain.voucher.controller;

import com.example.commandlineapplication.domain.voucher.VoucherType;
import com.example.commandlineapplication.domain.voucher.service.VoucherService;
import com.example.commandlineapplication.global.io.Command;
import com.example.commandlineapplication.global.io.Console;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherController {

  private final Console console;
  private final VoucherService voucherService;

  public boolean handleCommand(Command command) {
    switch (command) {
      case CREATE:
        VoucherType inputVoucherType = console.selectVoucherTypeOption();
        Integer inputDiscount = console.selectDiscount();

        voucherService.createVoucher(inputVoucherType, inputDiscount);
        return true;
      case DELETE:
        console.printDeleteUUID();
        String inputUUID = console.input();

        voucherService.deleteVoucher(UUID.fromString(inputUUID));
        return true;
      case LIST:
        voucherService.printHistory();
        return true;
      case EXIT:
        return false;
    }
    return false;
  }

}
