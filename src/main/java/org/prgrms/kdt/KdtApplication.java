package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtApplication {
	private static  final Logger logger= LoggerFactory.getLogger(KdtApplication.class);

	public static void main(String[] args) {

		var applicationContext=SpringApplication.run(KdtApplication.class, args);

	}

}
