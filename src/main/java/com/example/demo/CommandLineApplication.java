package com.example.demo;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerService;
import com.example.demo.customer.FileCustomerRepository;
import com.example.demo.exception.ExitException;
import com.example.demo.exception.WrongInputException;
import com.example.demo.voucher.Voucher;
import com.example.demo.voucher.VoucherContext;
import com.example.demo.voucher.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class CommandLineApplication {
	private Scanner scanner;
	private Command cmd;

	public enum Command {
		EXIT("exit") {
			@Override
			public void execute(CommandLineApplication commandLineApplication, VoucherService voucherService) throws ExitException {
				throw new ExitException();
			}
		},
		CREATE("create") {
			@Override
			public void execute(CommandLineApplication commandLineApplication, VoucherService voucherService) throws Exception {
				try {
					voucherService.addVoucher(commandLineApplication.getVoucherType(), commandLineApplication.getVoucherDiscountAmount());
				}
				catch (Exception e) {
					throw e;
				}
			}
		},
		LIST("list") {
			@Override
			public void execute(CommandLineApplication commandLineApplication, VoucherService voucherService) {
				voucherService.getVouchers().stream().forEach(System.out::println);
			}
		};

		private final String command;
		private static final Map<String, Command> commandMap = Stream.of(values()).collect(toMap(Object::toString, (e) -> e));

		Command(String command) {
			this.command = command;
		}

		@Override
		public String toString() {
			return command;
		}

		public static Optional<Command> fromString(String command) {
			return Optional.ofNullable(commandMap.get(command));
		}

		public void execute(CommandLineApplication commandLineApplication, VoucherService voucherService) throws Exception, ExitException {
		}
	}

	public CommandLineApplication() {
		this.scanner =  new Scanner(System.in);
	}

	public CommandLineApplication(InputStream is) {
		this.scanner =  new Scanner(is);
	}

	public static void main(String[] args) throws IOException {
		CommandLineApplication commandLineApplication = new CommandLineApplication();
		ApplicationContext voucherContext = new AnnotationConfigApplicationContext(VoucherContext.class);
		VoucherService voucherService = voucherContext.getBean("voucherService",VoucherService.class);
		File file = voucherContext.getResource("file: customer_blacklist.csv").getFile();

		//voucherContext.getBean("FileCustomerService", "customer_blacklist.csv");
		voucherContext.getBean("CustomService", CustomerService.class);

		while (true) {
			commandLineApplication.showMenu();
			try {
				Command.fromString(commandLineApplication.getCmd()).orElseThrow().execute(commandLineApplication, voucherService);
			}
			catch (ExitException e) {
				break;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showMenu() {
		this.showMsg("=== Voucher Program ===\n" +
				"Type exit to exit the program.\n" +
				"Type create to create a new voucher.\n" +
				"Type list to list all vouchers.\n");
	}

	public void showMsg(String msg) {
		System.out.print(msg);
	}

	public String get() throws WrongInputException {
		String input;
		try {
			input = scanner.nextLine();
		}
		catch (Exception e) {
			throw new WrongInputException();
		}
		return input;
	}

	public String getCmd() throws WrongInputException {
		return get();
	}

	public String getVoucherType() throws WrongInputException {
		return get();
	}

	public Float getVoucherDiscountAmount() throws WrongInputException {
		try {
			return Float.valueOf(get());
		}
		catch(Exception e) {
			throw new WrongInputException();
		}
	}
}
