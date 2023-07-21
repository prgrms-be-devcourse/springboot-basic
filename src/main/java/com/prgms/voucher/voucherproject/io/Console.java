package com.prgms.voucher.voucherproject.io;

import com.prgms.voucher.voucherproject.AppType;
import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.domain.customer.CustomerType;
import com.prgms.voucher.voucherproject.domain.voucher.MenuType;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Console {
	private static final Scanner sc = new Scanner(System.in);
	private static final Logger logger = LoggerFactory.getLogger(Console.class);

    public void printMessage(String message, boolean lnCheck) {
        if (lnCheck) {
            System.out.println(message);
            return;
        }

        System.out.print(message);
    }

	public void printVoucherInfo(Voucher voucher) {
		String message = MessageFormat.format("|UUID:{0} | VoucherType: {1} | percent:{2}|",
			voucher.getId(), voucher.getVoucherType().toString(), voucher.getDiscount());
		System.out.println(message);
	}

	public void printVoucherList(List<Voucher> vouchers) {
		if (vouchers.isEmpty()) {
			this.printMessage(Constant.NOT_EXITS_CUSTOMER, true);
		}

		for (Voucher v : vouchers) {
			this.printVoucherInfo(v);
		}
	}

	public Long inputDiscountAmount(VoucherType voucherType) {
		switch (voucherType) {
			case FIXED -> this.printMessage(Constant.CREATE_FIXED_VOUCHER, true);
			case PERCENT -> this.printMessage(Constant.CREATE_PERCENT_VOUCHER, true);
		}
		Integer selectedNum = this.getNumber();

		if (selectedNum == null)
			return null;

		Long discount = Long.valueOf(selectedNum);

		if (discount == null)
			return null;

		return discount;
	}

	public AppType inputAppService() {
		this.printMessage(Constant.CONSOLE_APP_MENU, true);
		String appType = sc.nextLine();

		try {
			return AppType.getSelectedAppType(appType);
		} catch (InputMismatchException e) {
			logger.error("MenuType InputMismatchException -> {}", appType);
			this.printMessage(Constant.WRONG_COMMAND, true);
			return null;
		}
	}

	public CustomerType inputCustomerMenu() {
		this.printMessage(Constant.CONSOLE_CUSTOMER_MENU, true);
		String customerType = sc.nextLine();

		try {
			return CustomerType.getSelectedCustomerType(customerType);
		} catch (InputMismatchException e) {
			logger.error("MenuType InputMismatchException -> {}", customerType);
			this.printMessage(Constant.WRONG_COMMAND, true);
			return null;
		}
	}

	public MenuType inputVoucherMenu() {
		this.printMessage(Constant.CONSOLE_VOUCHER_MENU, true);
		String menuType = sc.nextLine();

		try {
			return MenuType.getSelectedMenuType(menuType);
		} catch (InputMismatchException e) {
			logger.error("MenuType InputMismatchException -> {}", menuType);
			this.printMessage(Constant.WRONG_COMMAND, true);
			return null;
		}
	}

	public VoucherType inputVoucherType() {
		this.printMessage(Constant.CONSOLE_VOUCHER_TYPE, false);
		Integer selectedNum = getNumber();

		if (selectedNum == null)
			return null;

		try {
			return VoucherType.getSelectedVoucherType(selectedNum);
		} catch (Exception e) {
			logger.error("VoucherType InputMismatchException -> {}", selectedNum);
			this.printMessage(e.getLocalizedMessage(), true);
			return null;
		}
	}

	private Integer getNumber() {
		int selectedNum = Integer.MIN_VALUE;
		try {
			selectedNum = sc.nextInt();
		} catch (InputMismatchException e) {
			logger.error("VoucherType InputMismatchException -> {}", selectedNum);
			return null;
		}
		sc.nextLine();
		return selectedNum;
	}

	/* Customer */
	public Customer inputCreateCustomer() {
		this.printMessage("등록할 email을 입력하세요: ", false);
		String email = sc.nextLine().trim();

		if (!validEmail(email)) {
			this.printMessage(Constant.WRONG_EMAIL, true);
			return null;
		}

		this.printMessage("등록할 name을 입력: ", false);
		String name = sc.nextLine().trim();

		try {
			return new Customer(email, name);
		} catch (InputMismatchException e) {
			this.printMessage(e.getLocalizedMessage(), true);
			return null;
		}

	}

	public void printCustomerInfo(Customer customer) {
		String message = MessageFormat.format("|email:{0} | name: {1} | createdDate:{2}|",
			customer.getEmail(), customer.getName(), customer.getCreatedAt().toString());
		System.out.println(message);
	}

	public void printCustomerList(List<Customer> customers) {
		if (customers.isEmpty()) {
			this.printMessage(Constant.NOT_EXITS_CUSTOMER, true);
		}

		for (Customer c : customers) {
			this.printCustomerInfo(c);
		}
	}

	public String inputEmail() {
		this.printMessage("email을 입력하세요: ", false);
		String email = sc.nextLine().trim();

		if (!validEmail(email)) {
			throw new InputMismatchException(Constant.WRONG_EMAIL);
		}

		return email;
	}

	private static boolean validEmail(String email) {
		return Pattern.matches(
			"([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$"
			, email);
	}
}
