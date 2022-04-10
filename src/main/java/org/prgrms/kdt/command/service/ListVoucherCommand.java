package org.prgrms.kdt.command.service;

import java.util.List;
import java.util.Locale;
import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Qualifier("LIST")
@Component
public class ListVoucherCommand implements Command {

  private final VoucherService voucherService;
  private final MessageSource messageSource;

  public ListVoucherCommand(
      VoucherService voucherService, MessageSource messageSource) {
    this.voucherService = voucherService;
    this.messageSource = messageSource;
  }

  @Override
  public String execute() {
    List<Voucher> vouchers = voucherService.findAll();
    String menu = messageSource.getMessage("menu.list", null, Locale.ROOT);
    return vouchers.stream().reduce(menu, (a, b) -> a + "\n" + b.toString(), String::concat);
  }
}