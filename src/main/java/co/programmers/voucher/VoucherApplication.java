package co.programmers.voucher;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import co.programmers.voucher.controller.VoucherApplicationRunner;

@SpringBootApplication
public class VoucherApplication {
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
		applicationContext.getBean(VoucherApplicationRunner.class).run();
	}
}
