package org.prgrms.kdt.command;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

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
    var voucherTypeCode = input.readInt();
    var voucherType = VoucherType.of(voucherTypeCode);
    output.printLine(
        MessageFormat.format("Type discount {0} size", voucherType.getMeasurement()));
    var amount = input.readLong();
    var voucherDto = new VoucherDto(UUID.randomUUID(), null, amount, voucherType,
        LocalDateTime.now());
    voucherService.create(voucherDto);
    return MessageFormat.format("{0} voucher created", voucherType.name().toLowerCase());
  }
}