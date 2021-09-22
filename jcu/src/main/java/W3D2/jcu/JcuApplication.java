package W3D2.jcu;

import W3D2.jcu.voucher.service.VoucherService;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JcuApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext ac = SpringApplication.run(JcuApplication.class, args);
		VoucherService voucherService = ac.getBean(VoucherService.class);
		voucherService.loadStorage();
		String command;
		boolean loop = true;
		while(loop) {
			System.out.print("\n===== Voucher Program =====\n"
				+ "Type exit to exit the program.\n"
				+ "Type create to create a new voucher.\n"
				+ "Type list to list all vouchers.\n"
				+ ">> ");
			command = Utils.readLine();
			CommandType code = CommandType.from(command);
			loop = code.execute(voucherService);
		}

	}

}
