package org.programmers.devcourse.voucher;

import org.programmers.devcourse.voucher.engine.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class VoucherApplication {

  public static void main(String[] args) {
    var app = new SpringApplication(VoucherApplication.class);
    if (app.getAdditionalProfiles().isEmpty()) {
      app.setAdditionalProfiles("dev");
    }
    var context = app.run(args);

    context.getBean(Controller.class).start();


  }


}
