package com.prgrms.commandLineApplication.execute;

import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.service.VoucherService;
import com.prgrms.commandLineApplication.voucher.dto.VoucherCreateDto;
import com.prgrms.commandLineApplication.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherExecution implements Execution {

  private final Console console;
  private final VoucherService voucherService;

  public VoucherExecution(Console console, VoucherService voucherService) {
    this.console = console;
    this.voucherService = voucherService;
  }

  @Override
  public void printList() {
    List<VoucherResponseDto> list = voucherService.findAllVouchers();
    console.printAllVouchers(list);
  }

  @Override
  public void create() {
    console.requestVoucherType();
    String voucherType = console.readVoucherType();

    console.requestDiscountAmount();
    int discountAmount = console.readVoucherAmount();

    VoucherCreateDto requestDto = new VoucherCreateDto(voucherType, discountAmount);
    voucherService.create(requestDto);
    console.printCreateVoucherSuccess(voucherType, discountAmount);
  }

}
