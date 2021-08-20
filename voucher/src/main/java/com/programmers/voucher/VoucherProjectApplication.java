package com.programmers.voucher;

import com.programmers.voucher.config.ServiceConfiguration;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.voucher.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VoucherProjectApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ServiceConfiguration.class);

		VoucherService voucherService = applicationContext.getBean(VoucherService.class);
		voucherService.openStorage();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("\n\n=== Voucher Program === \n" + "Type exit to exit the program. \n"
					+ "Type create to create a new voucher. \n" + "Type list to list all vouchers.");
			String command = br.readLine();
			switch (command) {
				case "exit":
					voucherService.closeStorage();
					System.exit(0);
					break;

				case "create":
					System.out.println("Input voucher name: "); // print() blocked??
					String voucherName = br.readLine();
					System.out.println("Select voucher type(Fixed / Percent): (Fixed)");
					String voucherType = br.readLine();

					Voucher createdVoucher;
					switch (voucherType) {
						case "Fixed":
							createdVoucher = voucherService.create(voucherName, Voucher.type.FIXED, 1000);
							break;

						case "Percent":
							createdVoucher = voucherService.create(voucherName, Voucher.type.PERCENT, 0.1);
							break;

						default:
							System.out.println(
									"Didn't select voucher type or invalid voucher type. Fallback to \"Fixed\" voucher type.");
							createdVoucher = voucherService.create(voucherName, Voucher.type.FIXED, 1000);
					}
					System.out.println(createdVoucher.toString());
					break;

				case "list":
					System.out.println("======= [ VOUCHERS ] =======");
					voucherService.listAll().forEach(System.out::println);
					System.out.println("============================");
					break;
			}
		}
	}

}
