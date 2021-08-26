package com.programmers.voucher;

import com.programmers.voucher.config.ApplicationMessages;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.customer.CustomerInitializer;
import com.programmers.voucher.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.*;

@SpringBootApplication
public class VoucherProjectApplication {

	private static ApplicationContext applicationContext;
	private static ApplicationMessages applicationMessages;
	private static VoucherService voucherService;
	private static CustomerInitializer blacklist;
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final Logger log = LoggerFactory.getLogger(VoucherProjectApplication.class);

	public enum Command {
		CREATE("create", () -> {
			String voucherName = "DEFAULT_VOUCHER_NAME";
			try {
				System.out.print(applicationMessages.getRequireName());
				voucherName = br.readLine().strip();
			} catch (IOException ex) {
				log.error("IOException occur when input voucher name. Fallback to 'DEFAULT_VOUCHER_NAME'...");
			}
			if(voucherName.isBlank()) voucherName = "DEFAULT_VOUCHER_NAME";

			DiscountPolicy.Type voucherType = DiscountPolicy.Type.FIXED;
			try {
				System.out.print(applicationMessages.getRequireType());
				voucherType = DiscountPolicy.Type.of(br.readLine());
			} catch (IOException ex) {
				log.error("IOException occur when input voucher type. Fallback to 'FIXED' type...");
			}

			int voucherAmount = 0;
			try {
				System.out.print(applicationMessages.getRequireAmount());
				voucherAmount = Integer.parseInt(br.readLine());
			} catch (IOException | NumberFormatException ex) {
				log.error("IOException occur when input voucher amount. Fallback to 0...");
			}

			System.out.println(voucherService.create(voucherName, voucherType, voucherAmount));
		}),
		LIST("list", () -> {
			System.out.println("======= [ VOUCHERS ] =======");
			voucherService.listAll().forEach(System.out::println);
			System.out.println("============================");
		}),
		BLACKLIST("blacklist", () -> {
			System.out.println("====== [ BLACKLIST ] ======");
			blacklist.readCustomers().forEach(System.out::println);
			System.out.println("===========================");
		}),
		INTRO("intro", () -> {
			System.out.println(applicationMessages.getIntroMessage());
		}),
		UNKNOWN("unknown", () -> {
			// do nothing
		}),
		TEST("test", () -> {
			voucherService.listAll().forEach(voucher -> {
				int discounted = voucher.getDiscountPolicy().discount(5000);
				System.out.printf("discounted %d to %d using %s.%n", 5000, discounted, voucher);
			});
		});

		String name;
		Runnable behavior;

		Command(String name, Runnable behavior) {
			this.name = name;
			this.behavior = behavior;
		}

		public static Command of(String input) {
			try {
				return Command.valueOf(input.toUpperCase());
			} catch (IllegalArgumentException ex) {
				return Command.UNKNOWN;
			}
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static void main(String[] args) throws IOException {
		applicationContext = SpringApplication.run(VoucherProjectApplication.class);

		voucherService = applicationContext.getBean(VoucherService.class);
		voucherService.openStorage();

		blacklist = applicationContext.getBean(CustomerInitializer.class);
		blacklist.loadCustomers();
		Command.BLACKLIST.behavior.run();

		applicationMessages = applicationContext.getBean(ApplicationMessages.class);

		for(String input = ""; !"exit".equals(input); input = br.readLine()) {
			Command command = Command.of(input);
			command.behavior.run();
			Command.INTRO.behavior.run();
		}

		voucherService.closeStorage();
	}
}
