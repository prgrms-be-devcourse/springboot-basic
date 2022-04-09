package org.prgrms.kdt;

import org.prgrms.kdt.domain.command.Command;
import org.prgrms.kdt.domain.console.Console;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
		VoucherService voucherService = context.getBean(VoucherService.class);

		while (true) {
			String commandInput = Console.inputCommand();
			Command command = Command.findCommand(commandInput);
			if(command == Command.CREATE){
				String voucherInput = Console.inputVoucherType();
				VoucherType voucherType = VoucherType.findVoucherType(voucherInput);
				long discount = Console.inputDiscount();
				voucherService.save(voucherType, discount);
			} else if(command == Command.LIST) {
				List<Voucher> vouchers = voucherService.findAll();
				Console.printAllVouchers(vouchers);
			} else if(command == Command.EXIT) {
				Console.printExit();
				break;
			}
		}

	}

}
