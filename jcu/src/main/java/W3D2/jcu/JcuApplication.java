package W3D2.jcu;

import W3D2.jcu.voucher.VoucherService;
import java.io.IOException;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class JcuApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);

		var voucherService = ac.getBean(VoucherService.class);
		new CommandLineApplication(voucherService);
	}

}
