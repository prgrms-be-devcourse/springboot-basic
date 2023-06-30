package co.programmers.voucher_management.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.dto.CustomerResponseDTO;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.repository.CustomerRepository;

@Service
public class CustomerInquiryService {
	private final CustomerRepository customerRepository;

	public CustomerInquiryService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	private static CustomerResponseDTO mapToDResponseDTO(Customer customer) {
		return CustomerResponseDTO.builder()
				.name(customer.getName())
				.build();
	}

	public Response inquireBlackList() {
		List<Customer> inquiredData = customerRepository.findByRating(Customer.Rating.BLACKLIST);
		List<CustomerResponseDTO> customerResponseDTOS = inquiredData.stream()
				.map(CustomerInquiryService::mapToDResponseDTO)
				.collect(Collectors.toList());
		return Response.builder()
				.state(Response.State.SUCCESS)
				.responseData(customerResponseDTOS)
				.build();
	}
}
