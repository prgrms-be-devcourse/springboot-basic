package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
  public static void main(String[] args) {
    var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    applicationContext.getBean(VoucherService.class).run();
  }
}
