package com.example.commandlineapplication.domain.voucher.controller;

import com.example.commandlineapplication.domain.voucher.dto.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import com.example.commandlineapplication.domain.voucher.service.VoucherFactory;
import com.example.commandlineapplication.domain.voucher.service.VoucherService;
import com.example.commandlineapplication.global.io.Command;
import com.example.commandlineapplication.global.io.Input;
import com.example.commandlineapplication.global.io.Output;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoucherController implements Runnable {

  private final Input input;
  private final Output output;
  private final VoucherFactory voucherFactory;
  private final VoucherService voucherService;

  @Override
  public void run() {
    while (true) {
      output.printMenu();
      Command command = Command.of(input.selectOption());

      switch (command) {
        case EXIT:
          return;
        case CREATE:
          output.printCreateOption();

          VoucherType inputVoucherType = VoucherType.of(input.selectOption());

          output.printDiscount();

          Integer discount = input.getDiscount();

          VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(inputVoucherType,
              discount);
          Voucher voucher = voucherFactory.create(voucherCreateRequest);
          voucherService.insert(voucher);
          continue;
        case LIST:
          voucherService.history();
      }
    }
  }
}
