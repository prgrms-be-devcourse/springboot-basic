package co.programmers.voucher_management.customer.entity;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import co.programmers.voucher_management.exception.NameFormatException;
import co.programmers.voucher_management.exception.RatingTypeException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Customer {
	private static Pattern NAME_FORMAT = Pattern.compile("^([가-힣]{2,30}|[a-zA-Z]{2,50})$");
	private static Pattern PHONE_NUM_FORMAT = Pattern.compile("^01([0|1|6|7|8|9])-\\\\d{3,4}-\\\\d{4}$");
	long id;
	String name;
	String rating;
	String phoneNumber;
	char status;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;

	@Builder
	public Customer(long id, String name, String rating, String phoneNumber) {
		validatePhoneNumber(phoneNumber);
		validateName(name);
		validateRating(rating);

		this.id = id;
		this.name = name;
		this.rating = rating;
		this.phoneNumber = phoneNumber;
	}

	private void validateRating(String rating) {
		try {
			Rating.valueOf(rating.toUpperCase());
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new RatingTypeException();
		}
	}

	private void validateName(String name) {
		if (!NAME_FORMAT.matcher(name)
				.matches()) {
			throw new NameFormatException();
		}
	}

	private void validatePhoneNumber(String phoneNumber) {
		if (!PHONE_NUM_FORMAT.matcher(phoneNumber)
				.matches()) {
			throw new NameFormatException();
		}
	}

	public enum Rating {
		NORMAL("normal"),
		BLACKLIST("blacklist");

		private final String meaning;

		Rating(String meaning) {
			this.meaning = meaning;

		}
	}
}
