package team.marco.voucher_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import team.marco.voucher_management_system.console_app.application.CommandMainApplication;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ConsoleApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ConsoleApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);

        ConfigurableApplicationContext context = springApplication.run(args);
        CommandMainApplication application = context.getBean(CommandMainApplication.class);

        application.run();
    }
}
