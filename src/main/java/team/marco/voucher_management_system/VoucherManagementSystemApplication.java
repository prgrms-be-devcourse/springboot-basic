package team.marco.voucher_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import team.marco.voucher_management_system.view.consoleapp.VoucherApplication;

@SpringBootApplication
public class VoucherManagementSystemApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(VoucherManagementSystemApplication.class, args);

        VoucherApplication application = context.getBean(VoucherApplication.class);
        application.run();
    }
}
