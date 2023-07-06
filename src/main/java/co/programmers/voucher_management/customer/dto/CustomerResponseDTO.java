package co.programmers.voucher_management.customer.dto;

import java.text.MessageFormat;

import co.programmers.voucher_management.customer.entity.Customer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerResponseDTO {
	long id;
	String name;
	String phoneNumber;
	String rating;

	@Builder
	public CustomerResponseDTO(Customer customer) {
		id = customer.getId();
		name = customer.getName();
		phoneNumber = customer.getPhoneNumber();
		rating = customer.getRating();
	}

	@Override
	public String toString() {
		return MessageFormat.format("id : {0}, Name : {1}, phone number : {2}, rating : {3} ",
				id, name, phoneNumber, rating);
	}
}
