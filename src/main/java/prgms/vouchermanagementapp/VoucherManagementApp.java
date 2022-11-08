package prgms.vouchermanagementapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.vouchermanagementapp.config.AppConfig;
import prgms.vouchermanagementapp.controller.CommandExecutor;

@SpringBootApplication
public class VoucherManagementApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagementApp.class, args);
    }

    @Override
    public void run(String... args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        CommandExecutor controller = applicationContext.getBean(CommandExecutor.class);

        controller.run();
    }
}
