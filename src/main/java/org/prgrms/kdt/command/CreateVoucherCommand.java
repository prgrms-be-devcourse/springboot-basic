package org.prgrms.kdt.command;

import java.text.MessageFormat;
import java.util.Locale;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.domain.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Qualifier("CREATE")
@Component
public class CreateVoucherCommand implements Command {

  private static final Logger log = LoggerFactory.getLogger(CreateVoucherCommand.class);
  private final VoucherService voucherService;
  private final Input input;
  private final Output output;
  private final MessageSource messageSource;

  public CreateVoucherCommand(VoucherService voucherService, Input input,
      Output output, MessageSource messageSource) {
    this.voucherService = voucherService;
    this.input = input;
    this.output = output;
    this.messageSource = messageSource;
  }

  @Override
  public String execute() {
    output.printLine(messageSource.getMessage("menu.create", null, Locale.ROOT));

    try {
      var voucherType = VoucherType.of(input.readInt());
      output.printLine(
          MessageFormat.format("Type discount {0} size", voucherType.getMeasurement()));
      voucherService.create(voucherType, input.readLong());
      return MessageFormat.format("{0} voucher created", voucherType.name().toLowerCase());
    } catch (NumberFormatException e) {
      log.error("Invalid Input Error", e);
      return ErrorType.INVALID_INPUT.getErrorMessage();
    } catch (IllegalArgumentException e) {
      log.error("Invalid Discount Error", e);
      return ErrorType.INVALID_DISCOUNT.getErrorMessage(e.getMessage());
    }
  }
}