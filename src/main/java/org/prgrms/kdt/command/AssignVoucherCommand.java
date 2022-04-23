package org.prgrms.kdt.command;

import java.text.MessageFormat;
import java.util.UUID;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class AssignVoucherCommand implements Command {

  private final Input input;
  private final Output output;
  private final VoucherService voucherService;
  private final CustomerService customerService;

  public AssignVoucherCommand(
      Input input,
      Output output,
      VoucherService voucherService,
      CustomerService customerService) {
    this.input = input;
    this.output = output;
    this.voucherService = voucherService;
    this.customerService = customerService;
  }

  @Override
  public String execute() {
    output.printLine("Enter the voucher code");
    var voucherId = UUID.fromString(input.read());
    output.printLine("Enter the customer id");
    var customerId = UUID.fromString(input.read());
    var customer = customerService.findById(customerId);
    if (customer.isEmpty()) {
      return MessageFormat.format("Customer with id {0} does not exist", customerId);
    }

    var voucher = voucherService.assign(voucherId, customerId);
    return MessageFormat.format("Voucher {0} is assigned to {1}",
        voucher.getVoucherId(), customerId);
  }
}