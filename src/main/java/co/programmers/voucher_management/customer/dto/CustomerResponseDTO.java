package co.programmers.voucher_management.customer.dto;

import java.text.MessageFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {
	String name;

	@Override
	public String toString() {
		return MessageFormat.format("Name : {0}", name);
	}
}
