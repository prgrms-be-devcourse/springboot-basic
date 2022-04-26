package org.prgrms.kdt.command;

import java.util.Locale;
import org.prgrms.kdt.dto.CustomerDto;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class RegisterCustomerCommand implements Command {

  private static final Logger log = LoggerFactory.getLogger(RegisterCustomerCommand.class);
  private final CustomerService customerService;
  private final Input input;
  private final Output output;
  private final MessageSource messageSource;

  public RegisterCustomerCommand(CustomerService customerService, Input input,
      Output output, MessageSource messageSource) {
    this.customerService = customerService;
    this.input = input;
    this.output = output;
    this.messageSource = messageSource;
  }

  @Override
  public String execute() {
    output.printLine(messageSource.getMessage("menu.register", null, Locale.getDefault()));
    output.printLine("Type name");
    var name = input.read();
    output.printLine("Type email");
    var email = input.read();
    var customerDto = new CustomerDto(name, email);
    customerService.register(customerDto);
    return "Customer created";
  }
}