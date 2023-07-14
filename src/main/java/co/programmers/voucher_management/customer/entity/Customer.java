package co.programmers.voucher_management.customer.entity;

import java.util.regex.Pattern;

import co.programmers.voucher_management.exception.InvalidDataException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Customer {
	private static final Pattern NAME_FORMAT = Pattern.compile("^([가-힣]{2,30}|[a-zA-Z]{2,50})$");
	private static final Pattern PHONE_NUM_FORMAT = Pattern.compile("^01([0|1|6|7|8|9])-\\d{3,4}-\\d{4}$");
	long id;
	String name;
	Rating rating;
	String phoneNumber;
	Status status;

	@Builder
	public Customer(long id, String name, Rating rating, String phoneNumber) {
		validatePhoneNumber(phoneNumber);
		validateName(name);
		// validateRating(rating);

		this.id = id;
		this.name = name;
		this.rating = rating;
		this.phoneNumber = phoneNumber;
		status = Status.NORMAL;
	}

	private void validateName(String name) {
		if (!NAME_FORMAT.matcher(name)
				.matches()) {
			throw new InvalidDataException();
		}
	}

	private void validatePhoneNumber(String phoneNumber) {
		if (!PHONE_NUM_FORMAT.matcher(phoneNumber)
				.matches()) {
			throw new InvalidDataException();
		}
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", name='" + name + '\'' +
				", rating='" + rating + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", status=" + status +
				'}';
	}

	public enum Rating {
		NORMAL,
		BLACKLIST
	}
}
