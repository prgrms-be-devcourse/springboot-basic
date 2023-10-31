package study.dev.spring.customer.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.dev.spring.customer.application.dto.CustomerInfo;
import study.dev.spring.customer.application.dto.CustomerMapper;
import study.dev.spring.customer.domain.CustomerRepository;
import study.dev.spring.wallet.domain.Wallet;
import study.dev.spring.wallet.domain.WalletRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final WalletRepository walletRepository;
	private final CustomerRepository customerRepository;

	@Transactional(readOnly = true)
	public List<CustomerInfo> getAllCustomers() {
		return customerRepository.findAll()
			.stream()
			.map(CustomerMapper::toCustomerInfo)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<CustomerInfo> getCustomersByVoucher(String voucherId) {
		List<String> customerIds = walletRepository.findByVoucherId(voucherId)
			.stream()
			.map(Wallet::getCustomerId)
			.toList();

		return customerRepository.findByIds(customerIds)
			.stream()
			.map(CustomerMapper::toCustomerInfo)
			.toList();
	}
}
