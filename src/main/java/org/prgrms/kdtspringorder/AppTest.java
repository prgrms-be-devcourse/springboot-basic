package org.prgrms.kdtspringorder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppTest {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        AppConfigurationClass.class);
    App app = applicationContext.getBean(App.class);
    app.run();
  }
}
