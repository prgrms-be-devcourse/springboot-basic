package co.programmers.voucher_management.customer.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Customer {
	String name;
	Rating rating;
	public enum Rating{
		NORMAL,
		BLACKLIST;
	}
}
