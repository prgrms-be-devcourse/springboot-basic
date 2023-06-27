package programmers.org.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import programmers.org.voucher.controller.VoucherController;

@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
		applicationContext.getBean(VoucherController.class).run();
	}
}
