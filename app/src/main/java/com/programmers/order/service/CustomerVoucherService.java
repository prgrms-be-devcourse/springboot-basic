package com.programmers.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Customer;
import com.programmers.order.domain.CustomerVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.message.LogMessage;
import com.programmers.order.repository.customervoucher.CustomerVoucherRepository;

@Transactional(readOnly = true)
@Service
public class CustomerVoucherService {
	private static final Logger log = LoggerFactory.getLogger(CustomerVoucherService.class);

	private final CustomerVoucherRepository customerVoucherRepository;

	public CustomerVoucherService(CustomerVoucherRepository customerVoucherRepository) {
		this.customerVoucherRepository = customerVoucherRepository;
	}

	@Transactional
	public Optional<UUID> save(UUID customerId, UUID voucherId) {
		CustomerVoucher customerVoucher = new CustomerVoucher(UUID.randomUUID(), customerId,
				voucherId, LocalDateTime.now());

		try {
			customerVoucherRepository.insert(customerVoucher);

			return Optional.ofNullable(customerVoucher.getId());
		} catch (JdbcException.NotExecuteQuery e) {
			e.printStackTrace();
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);

			return Optional.empty();
		}
	}

	public boolean isDuplicatePublish(UUID customerId, UUID voucherId) {
		return customerVoucherRepository.isDuplicatePublish(customerId, voucherId);
	}

	public List<Voucher> getVouchersForCustomer(UUID customerId) {
		return customerVoucherRepository.findVouchersByCustomerId(customerId);
	}

	public List<Customer> getCustomerForVoucher(UUID voucherId) {
		return customerVoucherRepository.joinCustomers(voucherId);
	}

	public Optional<Voucher> findVoucherById(UUID voucherId) {
		return customerVoucherRepository.findVoucherByVoucherId(voucherId);
	}

	@Transactional
	public void deleteByCustomerIdAndVoucherId(UUID customerIdentity, UUID voucherIdentity) {
		customerVoucherRepository.deleteByCustomerIdAndVoucherId(customerIdentity, voucherIdentity);
	}
}
