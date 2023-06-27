package programmers.org.voucher;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import programmers.org.voucher.config.AppConfig;
import programmers.org.voucher.controller.VoucherController;

@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		VoucherController voucherController = applicationContext.getBean("voucherController", VoucherController.class);

		voucherController.run();
	}
}
