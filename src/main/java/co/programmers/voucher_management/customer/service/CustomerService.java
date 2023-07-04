package co.programmers.voucher_management.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.dto.CustomerResponseDTO;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.repository.CustomerRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Response inquireBlackList() {
		List<Customer> inquiredData = customerRepository.findByRating(Customer.Rating.BLACKLIST);
		List<CustomerResponseDTO> customerResponseDTOS = inquiredData.stream()
				.map(CustomerResponseDTO::new)
				.collect(Collectors.toList());
		return Response.builder()
				.state(Response.State.SUCCESS)
				.responseData(customerResponseDTOS)
				.build();
	}
}
