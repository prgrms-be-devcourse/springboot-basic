package org.prgrms.kdtspringorder;

import org.prgrms.kdtspringorder.io.abstraction.Input;
import org.prgrms.kdtspringorder.io.abstraction.Output;
import org.prgrms.kdtspringorder.io.implementation.ConsoleInput;
import org.prgrms.kdtspringorder.io.implementation.ConsoleOutput;
import org.prgrms.kdtspringorder.order.domain.implementation.Order;
import org.prgrms.kdtspringorder.order.repository.abstraction.OrderRepository;
import org.prgrms.kdtspringorder.voucher.repository.abstraction.VoucherRepository;
import org.prgrms.kdtspringorder.voucher.repository.implementation.MemoryVoucherRepository;
import org.prgrms.kdtspringorder.order.service.OrderService;
import org.prgrms.kdtspringorder.voucher.service.VoucherService;
import org.prgrms.kdtspringorder.voucher.validation.CommandValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigurationClass {

  @Bean
  public App app(Input input, Output output, VoucherService voucherService,
      CommandValidator commandValidator) {
    return new App(input, output, voucherService, commandValidator);
  }

  @Bean
  public CommandValidator commandValidator() {
    return new CommandValidator();
  }

  @Bean
  public Input input() {
    return new ConsoleInput();
  }

  @Bean
  public Output output() {
    return new ConsoleOutput();
  }

  @Bean
  public VoucherService voucherService(VoucherRepository voucherRepository) {
    return new VoucherService(voucherRepository);
  }

  @Bean
  public VoucherRepository voucherRepository() {
    return new MemoryVoucherRepository();
  }
}
