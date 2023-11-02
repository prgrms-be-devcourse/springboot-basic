package com.programmers.springbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = {
	@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.programmers.springbasic.command.*"),
	@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.programmers.springbasic.console.*")
})
public class VoucherApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
	}

}
