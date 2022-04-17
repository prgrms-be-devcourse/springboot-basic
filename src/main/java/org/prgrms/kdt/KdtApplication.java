package org.prgrms.kdt;

import org.prgrms.kdt.handler.CommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
    var commandHandler = context.getBean(CommandHandler.class);
    commandHandler.handle();
  }
}