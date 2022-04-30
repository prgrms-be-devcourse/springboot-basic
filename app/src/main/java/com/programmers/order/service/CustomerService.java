package com.programmers.order.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.domain.Customer;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.exception.DomainException;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.LogMessage;
import com.programmers.order.repository.customer.CustomerRepository;
import com.programmers.order.utils.TranslatorUtils;

@Service
@Transactional(readOnly = true)
public class CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
	private final CustomerRepository customerRepository;
	private final CustomerVoucherService customerVoucherService;

	public CustomerService(CustomerRepository customerRepository, CustomerVoucherService customerVoucherService) {
		this.customerRepository = customerRepository;
		this.customerVoucherService = customerVoucherService;
	}

	@Transactional
	public Optional<Customer> save(CustomerDto.SaveRequestDto saveRequestDto) {
		Customer customer = saveRequestDto.toCustomer();

		try {
			return Optional.of(customerRepository.insert(customer));
		} catch (JdbcException.NotExecuteQuery e) {
			log.info(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);

			return Optional.empty();
		}
	}

	@Transactional
	public Optional<Customer> update(CustomerDto.UpdateCustomer updateCustomer) {
		String email = updateCustomer.getEmail();

		try {
			Customer customer = customerRepository.findByEmail(email)
					.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));
			customer.changeName(updateCustomer);

			return Optional.of(customerRepository.update(customer));
		} catch (JdbcException.NotExecuteQuery e) {
			log.info(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);

			return Optional.empty();
		}
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Optional<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public boolean notExistByEmail(String email) {
		return customerRepository.findByEmail(email).isEmpty();
	}

	public Optional<Customer> findById(String uuid) {
		return customerRepository.findById(TranslatorUtils.toUUID(uuid.getBytes()));
	}

	@Transactional
	public Optional<UUID> registerVoucher(CustomerDto.RegisterVoucherDto registerVoucherDto) {

		try {
			Voucher voucher = customerVoucherService.findById(registerVoucherDto.getVoucherId())
					.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));
			Customer customer = this.findByEmail(registerVoucherDto.getEmail())
					.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));

			if (customerVoucherService.isDuplicatePublish(customer.getCustomerId(), voucher.getVoucherId())) {
				log.info(LogMessage.InfoLogMessage.getPrefix(), LogMessage.InfoLogMessage.DUPLICATE_VOUCHER_REGISTER);
				return Optional.empty();
			}

			return customerVoucherService.save(customer.getCustomerId(), voucher.getVoucherId());
		} catch (DomainException.NotFoundResource e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	public List<VoucherDto.Response> lookUpWithVouchers(String email) {
		try {
			Customer customer = this.findByEmail(email)
					.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));

			return customerVoucherService.getVouchersForCustomer(customer.getCustomerId())
					.stream()
					.map(voucher -> new VoucherDto.Response(
							voucher.getVoucherId(),
							Long.parseLong(voucher.getDiscountValue()),
							voucher.getCreatedAt()
					)).toList();
		} catch (DomainException.NotFoundResource e) {
			log.info(LogMessage.InfoLogMessage.getPrefix(),LogMessage.InfoLogMessage.CUSTOMER_NOT_EXIST_EMAIL);
		}

		return List.of();
	}

	public void unMappingVoucher(String email, UUID voucherIdentity) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.INTERNAL_PROGRAM_ERROR));
		UUID customerIdentity = customer.getCustomerId();
		customerVoucherService.deleteByCustomerIdAndVoucherId(customerIdentity, voucherIdentity);
	}
}
