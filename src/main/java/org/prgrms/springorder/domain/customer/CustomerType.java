package org.prgrms.springorder.domain.customer;

import java.util.Arrays;
import java.util.Objects;

import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.exception.NoSuchCustomerException;

public enum CustomerType {
	NORMAL("NORMAL"),
	BLACK("BLACK");

	private final String rating;

	CustomerType(String rating) {
		this.rating = rating;
	}

	public static CustomerType getCustomerTypeByRating(String rating) {
		return Arrays.stream(values())
			.filter(a -> Objects.equals(a.rating, rating))
			.findAny()
			.orElseThrow(() -> new NoSuchCustomerException(ErrorMessage.NO_SUCH_CUSTOMER_MESSAGE));
	}

	public String getRating() {
		return rating;
	}
}
