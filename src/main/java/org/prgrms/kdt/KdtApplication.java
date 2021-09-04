package org.prgrms.kdt;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(
		basePackages = {"org.prgrms.kdt.customer", "org.prgrms.kdt.voucher", "org.prgrms.kdt.order"}
)
public class KdtApplication {
	private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);
	public static void main(String[] args) throws IOException {
		var applicationContext = SpringApplication.run(KdtApplication.class);
		VoucherService voucherService = applicationContext.getBean(VoucherService.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String command;
		int input;

		System.out.println("=== Voucher Program ===\n");
		while (true) {
			System.out.println(
					"Type exit to exit the program.\n" +
							"Type create to create a new voucher.\n" +
							"Type list to list all vouchers.");
			command = br.readLine();
			switch (command) {
				case "create":
					System.out.println("1을 누르면 FixedAmountVoucher 생성, 2를 누르면 PercentDiscountVoucher 생성");
					input = Integer.parseInt(br.readLine());
					String message = input == 1 ? "FixedAmountVoucher 생성" : "PercentDiscountVoucher 생성";
					System.out.println(message);
					Voucher voucher = input == 1 ? new FixedAmountVoucher(UUID.randomUUID(), 1L) : new PercentDiscountVoucher(UUID.randomUUID(), 1L);
					voucherService.create(voucher);
					logger.info(voucher.toString()+"가 생성되었습니다.");
					break;
				case "list":
					System.out.println("바우처 조회");
					voucherService.list().forEach(System.out::println);
					logger.info("사용자가 바우처 목록을 조회했습니다.");
					break;
				case "exit":
					System.out.println("종료합니다.");
					logger.info("사용자가 프로그램을 종료했습니다.");
					break;
				default:
					System.out.println("잘못 입력했습니다.");
					logger.info("잘못된 입력이 들어왔습니다.");
					break;
			}
			if (command.equals("exit")) break;
		}
	}

}
