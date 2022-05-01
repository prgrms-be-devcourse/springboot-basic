package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.vouchermanagement.commons.exception.AlreadyExistException;
import com.prgrms.vouchermanagement.commons.exception.CreationFailException;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
	private final VoucherRepository voucherRepository;
	private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public List<Voucher> getAll() {
		return voucherRepository.findAll();
	}

	@Transactional
	public Optional<Voucher> create(VoucherType voucherType, long voucherDiscountInfo) {
		checkNull(voucherType, "voucherType 는 null 이 올 수 없습니다");
		checkNull(voucherDiscountInfo, "voucherDiscountInfo 는 null 이 올 수 없습니다");

		UUID id = UUID.randomUUID();

		checkDuplicatedId(id);

		Voucher voucher = null;

		try {
			voucher = voucherType.getVoucher(id, voucherDiscountInfo, LocalDateTime.now());

			voucherRepository.insert(voucher);

			logger.info("Publish new Voucher : {}", voucher);
		} catch (CreationFailException e) {
			logger.error("{}", e.getMessage(), e);
		}

		return Optional.ofNullable(voucher);
	}

	@Transactional
	public Optional<Voucher> getById(UUID id) {
		checkNull(id, "id 는 null 이 올 수 없습니다");
		checkDuplicatedId(id);

		return voucherRepository.findById(id);
	}

	public void checkNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	@Transactional(readOnly = true)
	protected void checkDuplicatedId(UUID id) {
		if(voucherRepository.findById(id)
			.isPresent()){
			throw new AlreadyExistException();
		}
	}

	@Transactional
	public void removeById(UUID voucherId) {
		checkNull(voucherId, "id 는 null 이 올 수 없습니다");
		checkDuplicatedId(voucherId);

		voucherRepository.deleteById(voucherId);
	}

}
