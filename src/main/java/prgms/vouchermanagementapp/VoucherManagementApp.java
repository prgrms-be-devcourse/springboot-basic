package prgms.vouchermanagementapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import prgms.vouchermanagementapp.controller.CommandExecutor;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "prgms.vouchermanagementapp.configuration")
public class VoucherManagementApp implements CommandLineRunner {

    private final CommandExecutor commandExecutor;

    public VoucherManagementApp(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagementApp.class, args);
    }

    @Override
    public void run(String... args) {
        commandExecutor.run();
    }
}
