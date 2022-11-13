package com.programmers.commandline;

import com.programmers.commandline.domain.voucher.VoucherApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAspectJAutoProxy
public class CommandLineApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(CommandLineApplication.class, args);
		applicationContext.getBean(VoucherApplication.class).run();
	}
}
