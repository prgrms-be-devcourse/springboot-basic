package org.prgrms.vouchermanagement.controller.console;

import org.prgrms.vouchermanagement.console.Input;
import org.prgrms.vouchermanagement.console.Output;
import org.prgrms.vouchermanagement.customer.service.CustomerService;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class ConsoleController {

  private final VoucherService voucherService;
  private final CustomerService customerService;
  private final Input input;
  private final Output output;

  public ConsoleController(VoucherService voucherService, CustomerService customerService, Input input, Output output) {
    this.voucherService = voucherService;
    this.customerService = customerService;
    this.input = input;
    this.output = output;
  }

  public void run() {
    boolean progress = true;
    while (progress) {
      progress = execute();
    }
  }

  public boolean execute() {
    String command = input.readCommand();
    return ConsoleCommand.valueOf(command.toUpperCase()).apply(this);
  }

  public void processList() {
    String listType = input.readListType().toLowerCase();
    if(listType.equals("customer")) {
      output.printList(customerService.getCustomerList());
    }
    else if(listType.equals("voucher")) {
      output.printList(voucherService.getVoucherList());
    }
  }

  public void nonExistentCommand() {
    output.printNotExistingCommand();
  }
}
