package org.prgrms.kdt.command;

import java.util.Locale;
import java.util.UUID;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.type.SearchType;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class SearchCommand implements Command {

  private final Output output;
  private final Input input;
  private final VoucherService voucherService;
  private final CustomerService customerService;
  private final MessageSource messageSource;

  public SearchCommand(
      Output output,
      Input input,
      VoucherService voucherService,
      CustomerService customerService, MessageSource messageSource) {
    this.output = output;
    this.input = input;
    this.voucherService = voucherService;
    this.customerService = customerService;
    this.messageSource = messageSource;
  }

  @Override
  public String execute() {
    output.printLine(messageSource.getMessage("menu.search", null, Locale.getDefault()));

    var searchType = SearchType.of(input.read());
    switch (searchType) {
      case SEARCH_VOUCHER -> {
        output.printLine("Enter customer id");
        var customerId = UUID.fromString(input.read());
        var vouchers = voucherService.findByCustomerId(customerId);
        return vouchers.stream()
            .reduce("", (acc, voucher) -> acc + voucher.toString() + "\n", (acc, voucher) -> acc);
      }
      case SEARCH_CUSTOMER -> {
        output.printLine("Enter voucher id");
        var voucherId = UUID.fromString(input.read());
        var customer = customerService.findCustomerByVoucherId(voucherId);
        return customer.toString();
      }
      default -> {
        return "Type customer or voucher";
      }
    }
  }
}