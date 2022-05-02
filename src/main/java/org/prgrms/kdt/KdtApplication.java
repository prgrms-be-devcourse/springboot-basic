package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class KdtApplication {

	public static void main(String[] args) {
		SpringApplication.run(KdtApplication.class, args);
	}
}
