package org.prgrms.kdt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:./application.properties")
@ServletComponentScan
@ComponentScan(
		basePackages = {"org.prgrms.kdt"}
)
public class KdtApplication {
	public static void main(String[] args) {
	}

}
