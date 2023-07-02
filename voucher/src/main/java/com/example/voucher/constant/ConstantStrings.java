package com.example.voucher.constant;

public class ConstantStrings {

	public static final String MESSAGE_PRINT_MODE_SELECTION= """
		=== Voucher Program ===
		Type exit to exit the program.
		Type create to create a new voucher.
		Type list to list all vouchers.
		""";
	public static final String MESSAGE_PRINT_TYPE_SELECTION = """
		Input Number for select VoucherType
		1. FixedAmount
		2. PercentDiscount
		  	""";
	public static final String MESSAGE_PRINT_DISCOUNT_PRICE = """
		input price for discount
		""";
	public static final String MESSAGE_PRINT_DISCOUNT_PERCENT = """
		input percent for discount
		""";

	public static final String FORMAT_PRINT_FIXED_AMOUNT_VOUCHER_INFO = "VoucherType : %s, discountAmount : %d";
	public static final String FORMAT_PRINT_PERCENT_VOUCHER_INFO = "VoucherType : %s, discountPercent : %d";

	public static final String MESSAGE_PRINT_RETRY_MODE_SELECTION_PROMPT= """
		MODE를 다시 선택해주세요
		""";
	public static final String MESSAGE_PRINT_RETRY_VOUCHER_TYPE_SELECTION_PROMPT ="""
 		Voucher Type을 다시 선택해주세요
 		""";

	public static final String PREFIX_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "IllegalArgumentException : ";
	public static final String PREFIX_INPUT_MISMATCH_EXCEPTION_MESSAGE = "InputMismatchException : ";
	public static final String PREFIX_EXCEPTION_MESSAGE = "Exception : ";

	public static final String MESSAGE_ERROR_INPUT_NUMBER = """
		숫자를 입력해주세요
		""";
	public static final String MESSAGE_ERROR_RANGE_CONSTRAINT = """
		퍼센트 값은 0과 100 사이여야 합니다
		""";
	public static final String MESSAGE_ERROR_NON_ZERO_CONSTRAINT = """
 		값은 0이 아니여야 합니다
 		""";
	public static final String MESSAGE_ERROR_POSITIVE_CONSTRAINT = """
 		값은 양수여야 합니다
 		""";
	public static final String FORMAT_ERROR_GREATER_THAN_CONSTRAINT = """
 		값은 threshold 보다 커야합니다.
 		""";

	private ConstantStrings() {

	}
}
