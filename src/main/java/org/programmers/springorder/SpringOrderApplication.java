package org.programmers.springorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringOrderApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringOrderApplication.class);

	public static void main(String[] args) {
		log.info("Voucher 관리 애플리케이션 구동");
		SpringApplication.run(SpringOrderApplication.class, args).close();
	}

}
