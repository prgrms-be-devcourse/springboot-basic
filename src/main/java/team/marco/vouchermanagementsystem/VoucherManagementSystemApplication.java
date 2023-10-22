package team.marco.vouchermanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import team.marco.vouchermanagementsystem.view.ConsoleVoucherApplication;

@SpringBootApplication
public class VoucherManagementSystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherManagementSystemApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(VoucherManagementSystemApplication.class, args);

        logger.info("Program start {}", context.getEnvironment().getActiveProfiles());

        ConsoleVoucherApplication application = context.getBean(ConsoleVoucherApplication.class);
        application.run();

        logger.info("Program exit");
    }
}
