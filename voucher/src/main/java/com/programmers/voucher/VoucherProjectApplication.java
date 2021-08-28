package com.programmers.voucher;

import com.programmers.voucher.config.ApplicationMessages;
import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.service.customer.CustomerService;
import com.programmers.voucher.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class VoucherProjectApplication {

	private static ApplicationContext applicationContext;
	private static ApplicationMessages applicationMessages;
	private static CustomerService customerService;
	private static VoucherService voucherService;
	private static CustomerService blacklistCustomerService;
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
			
			long customerId = 0;
			try {
				System.out.print(applicationMessages.getRequireCustomerId());
				customerId = Long.parseLong(br.readLine());
			} catch (IOException | NumberFormatException ex) {
				log.error("IOException or NumberFormatException occur when input customer id. {}...", ex.getMessage());
			}

			System.out.println(voucherService.create(voucherName, voucherType, voucherAmount, customerId));
		}),
		LIST("list", () -> {
			System.out.println("======= [ VOUCHERS ] =======");
			voucherService.listAll().forEach(System.out::println);
			System.out.println("============================");
		}),
		BLACKLIST("blacklist", () -> {
			System.out.println("====== [ BLACKLIST ] ======");
			blacklistCustomerService.listAll().forEach(System.out::println);
			System.out.println("===========================");
		}),
		INTRO("intro", () -> {
			System.out.println(applicationMessages.getIntroMessage());
		}),
		UNKNOWN("unknown", () -> {
			// do nothing
		}),
		USER("user", () -> {
			int randomNumber = new Random().nextInt();
			Customer customer = customerService.create("username" + randomNumber, "alias" + randomNumber);
			System.out.println("Created user " + customer);
		}),
		USERS("users", () -> {
			System.out.println("======== [ USERS ] ========");
			customerService.listAll().forEach(System.out::println);
			System.out.println("===========================");
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

		customerService = applicationContext.getBean("basicCustomerService", CustomerService.class);
		customerService.openStorage();

		blacklistCustomerService = applicationContext.getBean("blacklistCustomerService", CustomerService.class);
		blacklistCustomerService.openStorage();
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
