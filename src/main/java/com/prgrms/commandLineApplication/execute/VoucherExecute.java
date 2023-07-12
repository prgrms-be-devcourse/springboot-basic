package com.prgrms.commandLineApplication.execute;

import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.service.VoucherService;
import com.prgrms.commandLineApplication.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherExecute implements Execute {

  private final Console console;
  private final VoucherService voucherService;

  public VoucherExecute(Console console, VoucherService voucherService) {
    this.console = console;
    this.voucherService = voucherService;
  }

  @Override
  public void printList() {
    List<Voucher> list = voucherService.findAllVouchers();
    console.printAllVouchers(list);
  }

  @Override
  public void create() {
    console.requestVoucherType();
    String voucherType = console.readVoucherType();

    console.requestDiscountAmount();
    int discountAmount = console.readVoucherAmount();

    voucherService.create(voucherType, discountAmount);
    console.printCreateVoucherSuccess(voucherType, discountAmount);
  }
}
