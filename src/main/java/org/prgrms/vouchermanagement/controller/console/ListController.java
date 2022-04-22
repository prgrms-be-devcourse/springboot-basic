package org.prgrms.vouchermanagement.controller.console;

import org.prgrms.vouchermanagement.console.Input;
import org.prgrms.vouchermanagement.console.Output;
import org.prgrms.vouchermanagement.customer.service.CustomerService;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class ListController {
  private final CustomerService customerService;
  private final VoucherService voucherService;
  private final Input input;
  private final Output output;

  public ListController(CustomerService customerService, VoucherService voucherService, Input input, Output output) {
    this.customerService = customerService;
    this.voucherService = voucherService;
    this.input = input;
    this.output = output;
  }

  public void  processList() {
    String listType = input.readListType().toLowerCase();
    if(listType.equals("customer")) output.printList(customerService.getCustomerList());
    else if(listType.equals("voucher")) output.printList(voucherService.getVoucherList());
  }
}
