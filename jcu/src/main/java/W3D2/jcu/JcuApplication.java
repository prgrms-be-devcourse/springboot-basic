package W3D2.jcu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JcuApplication {

	public static void main(String[] args) {
		SpringApplication.run(JcuApplication.class, args);

		// Todo : enum
		// Todo : delete 커멘드 추가

		// ApplicationContext ac = new AnnotationConfigApplicationContext(JcuApplication.class);
		// var voucherService = ac.getBean(VoucherService.class);
		// new CommandLineApplication(voucherService);
	}

}
