package org.programmers.devcourse.voucher;

import java.util.Arrays;
import org.programmers.devcourse.voucher.engine.CliVoucherApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VoucherApplication {

  public static void main(String[] args) {
    var app = new SpringApplication(VoucherApplication.class);
    var context = app.run(args);
    if (Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("dev")) {
      context.getBean(CliVoucherApplication.class).start();
    }
  }
}
