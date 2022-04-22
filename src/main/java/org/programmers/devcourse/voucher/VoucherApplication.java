package org.programmers.devcourse.voucher;

import java.util.Arrays;
import org.programmers.devcourse.voucher.engine.CliController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.io.DefaultResourceLoader;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VoucherApplication {

  public static void main(String[] args) {

    var app = new SpringApplication(VoucherApplication.class);
    app.setResourceLoader(new DefaultResourceLoader());
    var context = app.run(args);
    if (Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("dev")) {
      context.getBean(CliController.class).start();
    }
  }
}
