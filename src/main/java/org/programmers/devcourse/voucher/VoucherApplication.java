package org.programmers.devcourse.voucher;

import org.programmers.devcourse.voucher.engine.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class VoucherApplication {

  public static void main(String[] args) {
    var app = new SpringApplication(VoucherApplication.class);

    var context = app.run(args);

    context.getBean(Controller.class).start();


  }


}
