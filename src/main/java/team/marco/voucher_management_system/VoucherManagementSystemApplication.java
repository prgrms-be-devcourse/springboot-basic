package team.marco.voucher_management_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import team.marco.voucher_management_system.view.console.VoucherApplication;

@SpringBootApplication
public class VoucherManagementSystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherManagementSystemApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(VoucherManagementSystemApplication.class, args);

        logger.info("Program start {}", context.getEnvironment().getActiveProfiles());

        VoucherApplication application = context.getBean(VoucherApplication.class);
        application.run();

        logger.info("Program exit");
    }
}
