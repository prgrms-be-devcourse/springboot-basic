package com.prgrms.vouchermanagement.io;

enum ConsoleCommand {
	SHOW_MENU_OPTION("==== Voucher Program ====\n"
		+ "Type exit to exit program.\n"
		+ "Type create to create a new voucher.\n"
		+ "Type list to list all vouchers."),
	SHOW_VOUCHER_TYPE_OPTION(
		"==== Please type Voucher Type ====\n"
		+"Type fixed to create a fixed amount voucher.\n"
		+"Type percent to create a percent discount voucher\n"),
	REQUEST_VOUCHER_INFO(
		"==== Please type Voucher Info ====\n"+
			"Type discount amount(or percent)\n"),
	EMPTY("비어있습니다");

	private final String message;

	ConsoleCommand(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
