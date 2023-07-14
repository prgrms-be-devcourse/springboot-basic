package co.programmers.voucher_management.customer.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.customer.dto.CustomerResponseDTO;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.repository.CustomerRepository;
import co.programmers.voucher_management.exception.EmptyAssignerException;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final VoucherRepository voucherRepository;
	private final OutputView outputView;

	public CustomerService(CustomerRepository customerRepository, VoucherRepository voucherRepository,
			OutputView outputView) {
		this.customerRepository = customerRepository;
		this.voucherRepository = voucherRepository;
		this.outputView = outputView;
	}

	public List<CustomerResponseDTO> inquireByRating(String rating) {
		List<Customer> inquiredData = customerRepository.findByRating(rating);
		return inquiredData.stream()
				.map(CustomerResponseDTO::new)
				.collect(Collectors.toList());
	}

	public void inquiryCustomerByVoucher(long voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId)
				.orElseThrow(
						() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", voucherId)));
		long customerId = voucher.getCustomerId();
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new EmptyAssignerException(
						MessageFormat.format("No customer assigned to voucher of id {0}", voucherId)));
		CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO(customer);
		outputView.print(customerResponseDTO);
		
	}
}
