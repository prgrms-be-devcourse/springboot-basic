package org.prgrms.kdt.command;

import java.util.Locale;
import java.util.UUID;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class DeleteVoucherCommand implements Command {

  private final VoucherService voucherService;
  private final Input input;
  private final Output output;
  private final MessageSource messageSource;

  public DeleteVoucherCommand(VoucherService voucherService, Input input, Output output,
      MessageSource messageSource) {
    this.voucherService = voucherService;
    this.input = input;
    this.output = output;
    this.messageSource = messageSource;
  }

  @Override
  public String execute() {
    output.printLine(messageSource.getMessage("menu.delete", null, Locale.getDefault()));
    var customerId = UUID.fromString(input.read());
    output.printLine("Type voucher id to delete");
    var voucherId = UUID.fromString(input.read());
    voucherService.delete(customerId, voucherId);
    return "Voucher deleted";
  }
}