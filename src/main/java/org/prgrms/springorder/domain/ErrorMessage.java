package org.prgrms.springorder.domain;

public enum ErrorMessage {

	NO_SUCH_VOUCHER_MESSAGE("""
		No Such Voucher
		"""),
	No_SUCH_COMMAND_MESSAGE("""
		No Such Command
		"""),
	FILE_LOAD_FAIL_MESSAGE("""
		File Load Failed
		"""),
	FILE_SAVE_FAIL_MESSAGE("""
		File Save Failed
		"""),
	FILE_READ_FAIL_MESSAGE("""
		File Read Failed
		"""),
	WRONG_AMOUNT_MESSAGE("""
		Amount out of range
		"""),
	WRONG_PERCENT_MESSAGE("""
		Percentage out of range
		""");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
