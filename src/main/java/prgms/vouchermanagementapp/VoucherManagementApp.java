package prgms.vouchermanagementapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import prgms.vouchermanagementapp.controller.CommandExecutor;

@SpringBootApplication
public class VoucherManagementApp implements CommandLineRunner {

    private final CommandExecutor commandExecutor;
    private final ApplicationContext applicationContext;

    public VoucherManagementApp(CommandExecutor commandExecutor, ApplicationContext applicationContext) {
        this.commandExecutor = commandExecutor;
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagementApp.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(applicationContext.getBean(CommandExecutor.class));
        commandExecutor.run();
    }
}
