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
  public App app(Input input, Output output, VoucherService voucherService, CommandValidator commandValidator) {
    return new App(input, output, voucherService, commandValidator);
  }

  @Bean
  CommandValidator commandValidator() {
    return new CommandValidator();
  }

  @Bean
  Input input(){
    return new ConsoleInput();
  }

  @Bean
  Output output(){
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

  @Bean
  public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
    return new OrderService(voucherService, orderRepository);
  }

  @Bean
  public OrderRepository orderRepository() {
    return new OrderRepository() {
      @Override
      public void insert(Order order) {

      }
    };
  }
}
