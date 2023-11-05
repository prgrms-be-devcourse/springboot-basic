package team.marco.voucher_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import team.marco.voucher_management_system.console_app.application.CommandMainApplication;
import team.marco.voucher_management_system.util.Console;

@SpringBootApplication
@ConfigurationPropertiesScan
public class VoucherManagementSystemApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(VoucherManagementSystemApplication.class, args);
        CommandMainApplication application = context.getBean(CommandMainApplication.class);

        application.run();

        Console.close();
    }
}
