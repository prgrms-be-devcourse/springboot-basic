package co.programmers.voucher_management.common;

import java.util.Arrays;

import co.programmers.voucher_management.exception.MenuTypeMismatchException;

public enum MenuDescription {
	VOUCHER("1",
			"Type '1' to create a new voucher.\n"
					+ "Type '2' to list all vouchers.\n"
					+ "Type '3' to modify a voucher.\n"
					+ "Type '4' to delete a voucher.\n"
					+ "Type '5' to assign a voucher to the customer.\n"
					+ "Type '6' to list vouchers of certain customer.\n"
					+ "Type '7' to delete a voucher of a certain customer.\n"
					+ "Type '8' to find a customer of certain voucher.\n"
	),
	CUSTOMER("2", "Type '1' to list customer blacklists.\n");
	private final String expression;
	private final String commandDescription;

	MenuDescription(String expression, String commandDescription) {
		this.expression = expression;
		this.commandDescription = commandDescription;
	}

	public static String of(String expression) {
		return Arrays.stream(MenuDescription.values())
				.filter(m -> m.expression.equals(expression))
				.findAny()
				.orElseThrow(() -> new MenuTypeMismatchException("Unsupported menu"))
				.description();
	}

	public String description() {
		return commandDescription;
	}

}
