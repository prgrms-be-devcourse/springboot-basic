package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.customer.service.CustomerService;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class ConsoleController {

  private final VoucherService voucherService;
  private final CustomerService customerService;

  public ConsoleController(VoucherService voucherService, CustomerService customerService) {
    this.voucherService = voucherService;
    this.customerService = customerService;
  }
}
