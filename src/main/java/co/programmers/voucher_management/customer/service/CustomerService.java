package co.programmers.voucher_management.customer.service;

import static co.programmers.voucher_management.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.customer.dto.CustomerResponseDTO;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.repository.CustomerRepository;
import co.programmers.voucher_management.exception.EmptyAssignerException;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final VoucherRepository voucherRepository;

	public CustomerService(CustomerRepository customerRepository, VoucherRepository voucherRepository) {
		this.customerRepository = customerRepository;
		this.voucherRepository = voucherRepository;
	}

	public List<CustomerResponseDTO> inquireByRating(String rating) {
		List<Customer> inquiredData = customerRepository.findByRating(rating);
		return inquiredData.stream()
				.map(CustomerResponseDTO::new)
				.toList();
	}

	public CustomerResponseDTO inquiryCustomerByVoucher(long voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId)
				.orElseThrow(() -> new NoSuchDataException(VOUCHER_NOT_FOUND));
		long customerId = voucher.getCustomerId();
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new EmptyAssignerException(CUSTOMER_NOT_FOUND));
		return new CustomerResponseDTO(customer);
	}
}
