package com.programmers.order.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.CustomerVoucher;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.message.ErrorLogMessage;
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
			log.error(ErrorLogMessage.getPrefix(), ErrorLogMessage.NOT_EXECUTE_QUERY);

			return Optional.empty();
		}
	}

	public boolean isDuplicatePublish(UUID customerId, UUID voucherId) {
		return customerVoucherRepository.isDuplicatePublish(customerId, voucherId);
	}

}
