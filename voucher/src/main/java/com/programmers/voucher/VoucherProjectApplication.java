package com.programmers.voucher;

import com.programmers.voucher.config.ApplicationMessages;
import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.customer.CustomerService;
import com.programmers.voucher.service.customer.CustomerVoucherService;
import com.programmers.voucher.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication
public class VoucherProjectApplication {

	private static ApplicationContext applicationContext;
	private static ApplicationMessages applicationMessages;
	private static CustomerVoucherService customerVoucherService;
	private static VoucherService voucherService;
	private static CustomerService blacklistCustomerService;
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final Logger log = LoggerFactory.getLogger(VoucherProjectApplication.class);

	public enum Command {
		CREATE_VOUCHER("create_voucher", () -> {
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
				voucherAmount = voucherType.getConstraint().apply(Integer.parseInt(br.readLine()));
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
		LIST_VOUCHER("list_voucher", () -> {
			System.out.println("======= [ VOUCHERS ] =======");
			voucherService.listAll().forEach(System.out::println);
			System.out.println("============================");
		}),
		READ_VOUCHER("read_voucher", () -> {
			try {
				System.out.print(applicationMessages.getRequireVoucherId());
				long voucherId = Integer.parseInt(br.readLine());
				final Optional<Voucher> byId = voucherService.findById(voucherId);
				if(byId.isEmpty())
					System.out.println("NO VOUCHER FOUND.");
				else
					System.out.println(byId.get());
			} catch (IOException | NumberFormatException ex) {
				log.error("IOException or NumberFormatException occur when input voucher id. {}...", ex.getMessage());
			}
		}),
		UPDATE_VOUCHER("update_voucher", () -> {
			try {
				System.out.print(applicationMessages.getRequireVoucherId());
				long voucherId = Integer.parseInt(br.readLine());
				voucherService.findById(voucherId).ifPresentOrElse(
						voucher -> {
							try {
								System.out.print(applicationMessages.getRequireUpdateField());
								final Voucher.UpdatableField field = Voucher.UpdatableField.of(br.readLine());
								System.out.print(applicationMessages.getRequireUpdateValue());
								voucher.update(field, br.readLine());
								voucherService.update(voucher);
							} catch (IOException ex) {
								log.error("IOException occur when input updating field.");
							}
						},
						() -> System.out.println("NO VOUCHER FOUND.")
				);
			} catch (IOException | NumberFormatException ex) {
				log.error("IOException or NumberFormatException occur when input voucher id. {}...", ex.getMessage());
			}
		}),
		DELETE_VOUCHER("delete_voucher", () -> {
			try {
				System.out.print(applicationMessages.getRequireVoucherId());
				long voucherId = Integer.parseInt(br.readLine());
				voucherService.delete(voucherId);
			} catch (IOException ex) {
				log.error("IOException occur when input voucher id. {}...", ex.getMessage());
			}
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
		CREATE_USER("create_user", () -> {
			int randomNumber = new Random().nextInt();
			Customer customer = customerVoucherService.create("username" + randomNumber, "alias" + randomNumber);
			System.out.println("Created user " + customer);
		}),
		LIST_USER("list_user", () -> {
			System.out.println("======== [ USERS ] ========");
			customerVoucherService.listAll().forEach(System.out::println);
			System.out.println("===========================");
		}),
		LIST_USER_VOUCHER("list_user_voucher", () -> {
			try {
				System.out.print(applicationMessages.getRequireCustomerId());
				long id = Long.parseLong(br.readLine());
				final List<Voucher> allByCustomer = customerVoucherService.findAllVoucherByCustomer(id);
				System.out.println("==== [ VOUCHERS ] ====");
				allByCustomer.forEach(System.out::println);
				System.out.println("======================");

			} catch (IOException ex) {
				log.error("IOException occur when input customer id. {}...", ex.getMessage());
			}
		}),
		READ_USER_BY_VOUCHER("read_user_by_voucher", () -> {
			try {
				System.out.print(applicationMessages.getRequireVoucherId());
				long id = Long.parseLong(br.readLine());
				customerVoucherService.findCustomerByVoucher(id).ifPresentOrElse(
						System.out::println,
						() -> {
							System.out.println("NO CUSTOMER FOUND.");
						}
				);
			} catch (IOException ex) {
				log.error("IOException occur when input customer id. {}...", ex.getMessage());
			}
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

		customerVoucherService = applicationContext.getBean("basicCustomerService", CustomerVoucherService.class);
		customerVoucherService.openStorage();

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
