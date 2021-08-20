package org.prgrms.kdtspringorder;

import java.util.List;
import org.prgrms.kdtspringorder.io.enums.CommandType;
import org.prgrms.kdtspringorder.io.abstraction.Input;
import org.prgrms.kdtspringorder.io.abstraction.Output;
import org.prgrms.kdtspringorder.io.domain.Command;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdtspringorder.io.exception.InvalidCommandException;
import org.prgrms.kdtspringorder.voucher.service.VoucherService;
import org.prgrms.kdtspringorder.voucher.validation.CommandValidator;
import org.springframework.stereotype.Component;

@Component
public class App {

  private final Input input;
  private final Output output;
  private final VoucherService voucherService;
  private final CommandValidator commandValidator;

  public App(Input input, Output output, VoucherService voucherService,
      CommandValidator commandValidator) {
    this.input = input;
    this.output = output;
    this.voucherService = voucherService;
    this.commandValidator = commandValidator;
  }

  public void run() {
    while (true) {
      output.print("=== Voucher Program ===\n"
          + "Type exit to exit the program.\n"
          + "Type create to create a new voucher.\n"
          + "Type list to list all vouchers.");

      Command command = input.read();

      try {
        commandValidator.validate(command);
      } catch (InvalidCommandException e) {
        System.out.println(e.getMessage());
        continue;
      }

      CommandType commandType = CommandType.of(command.getCommandName());

      if (commandType == CommandType.EXIT) {
        output.print("EXIT!");
        return;
      }

      switch (commandType) {
        case CREATE:
          output.print("=== 생성된 바우처 ===");
          Voucher createdVoucher = voucherService.createVoucher(command.getOptions().get(0));
          output.printVoucher(createdVoucher);
          break;

        case LIST:
          List<Voucher> vouchers = voucherService.getVouchers();
          output.print("=== 생성된 바우처 목록 ===");
          output.printVoucherList(vouchers);
          break;
      }
    }
  }
}
