package org.prgrms.vouchermanagement;

import org.prgrms.vouchermanagement.controller.console.ConsoleController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    applicationContext.getBean(ConsoleController.class).run();
  }
}
