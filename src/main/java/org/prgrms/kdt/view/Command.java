package org.prgrms.kdt.view;

public enum Command {

	CREATE("to exit the program."),
	LIST("to list all voucher."),
	EXIT("to exit the program.");

	private final String description;

	Command(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
