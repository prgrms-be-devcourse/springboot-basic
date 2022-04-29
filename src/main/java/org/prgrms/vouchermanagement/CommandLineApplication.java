package org.prgrms.vouchermanagement;

import org.prgrms.vouchermanagement.controller.console.ConsoleController;
import org.prgrms.vouchermanagement.voucher.repository.JdbcVoucherRepository;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommandLineApplication {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    applicationContext.getBean(ConsoleController.class).run();
  }
}
