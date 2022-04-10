package org.prgrms.kdtspringvoucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtSpringVoucherApplication {

	private static final Logger logger = LoggerFactory.getLogger(KdtSpringVoucherApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(KdtSpringVoucherApplication.class, args);
		logger.info("hihi");
	}

}
