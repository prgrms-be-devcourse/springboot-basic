package org.prgrms.vouchermanagement.controller.console;

import org.prgrms.vouchermanagement.console.Input;
import org.prgrms.vouchermanagement.console.Output;
import org.prgrms.vouchermanagement.customer.service.CustomerService;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class ConsoleController {

  private final ListController listController;
  private final VoucherService voucherService;
  private final CustomerService customerService;
  private final Input input;
  private final Output output;

  public ConsoleController(ListController listController, VoucherService voucherService, CustomerService customerService, Input input, Output output) {
    this.listController = listController;
    this.voucherService = voucherService;
    this.customerService = customerService;
    this.input = input;
    this.output = output;
  }

  public void run() {
    boolean progress = true;
    while (progress) {
      switch (input.readCommand()) {
        case "exit":
          progress = false;
          break;
        case "list":
          listController.processList();
          break;
        case "create":
          break;
        default:
          output.printNotExistingCommand();
      }
    }
  }


}
