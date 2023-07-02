package co.programmers.voucher_management.customer.dto;

import java.text.MessageFormat;

import co.programmers.voucher_management.customer.entity.Customer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponseDTO {
	String name;

	public CustomerResponseDTO(Customer customer) {
		name = customer.getName();
	}

	@Override
	public String toString() {
		return MessageFormat.format("Name : {0}", name);
	}
}
