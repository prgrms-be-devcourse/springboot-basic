package org.programmers.devcourse.voucher;

import org.programmers.devcourse.voucher.engine.Demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {


  public static void main(String[] args) {
    var context = SpringApplication.run(VoucherApplication.class, args);
    context.getBean(Demo.class).start();
  }


}
