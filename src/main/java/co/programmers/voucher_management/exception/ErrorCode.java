package co.programmers.voucher_management.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// Voucher
	VOUCHER_NOT_FOUND(HttpStatus.NOT_FOUND, "Voucher not found"),
	DUPLICATED_ASSIGNMENT(HttpStatus.BAD_REQUEST, "Already assigned voucher"),
	INVALID_FIXED_AMOUNT(HttpStatus.BAD_REQUEST, "Amount must be between 0 and 1,000,000"),

	INVALID_PERCENT_AMOUNT(HttpStatus.BAD_REQUEST, "Amount must be between 0 and 100"),
	INVALID_DISCOUNT_TYPE(HttpStatus.BAD_REQUEST, "Invalid discount type"),

	// Customer
	CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "Customer not found"),
	INVALID_NAME(HttpStatus.BAD_REQUEST, "Name must only be in Korean or English, up to 30 and 50 characters each"),
	INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "Invalid phone number"),

	// Common
	INVALID_COMMAND(HttpStatus.BAD_REQUEST, "Invalid command"),
	INVALID_MENU(HttpStatus.BAD_REQUEST, "Invalid menu");

	private final HttpStatus httpStatus;
	private final String message;
}
