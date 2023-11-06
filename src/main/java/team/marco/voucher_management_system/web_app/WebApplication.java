package team.marco.voucher_management_system.web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import team.marco.voucher_management_system.VoucherManagementSystemApplication;

@Import(VoucherManagementSystemApplication.class)
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
