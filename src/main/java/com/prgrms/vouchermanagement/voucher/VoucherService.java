package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.prgrms.vouchermanagement.commons.page.Pageable;
import com.prgrms.vouchermanagement.commons.exception.AlreadyExistException;
import com.prgrms.vouchermanagement.commons.exception.CreationFailException;
import com.prgrms.vouchermanagement.commons.exception.DeletionFailException;
import com.prgrms.vouchermanagement.commons.exception.NotExistException;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
	private final VoucherRepository voucherRepository;
	private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public List<Voucher> getAll(Pageable pageInfo) {
		Assert.notNull(pageInfo, "페이징 정보는 null 이 올 수 없습니다");

		return voucherRepository.findAll(pageInfo.offset(), pageInfo.limit());
	}

	public List<Voucher> getAll() {
		return voucherRepository.findAll();
	}

	@Transactional
	public Voucher create(VoucherType voucherType, long voucherDiscountInfo) throws
		CreationFailException,
		AlreadyExistException {
		try {
			checkNull(voucherType, "voucherType 는 null 이 올 수 없습니다");
			checkPositive(voucherDiscountInfo, "voucherDiscountInfo 는 0 보다 큰 정수여야 합니다");
		} catch (IllegalArgumentException e) {
			throw new CreationFailException(e);
		}
		UUID id = UUID.randomUUID();

		checkDuplicatedId(id);

		Voucher voucher = voucherType.getVoucher(id, voucherDiscountInfo, LocalDateTime.now());

		voucherRepository.insert(voucher);
		logger.info("VoucherService Publish new Voucher : {}", voucher);

		return voucher;
	}

	@Transactional
	public Voucher getById(UUID id) {
		checkNull(id, "id 는 null 이 올 수 없습니다");

		return voucherRepository.findById(id)
			.orElseThrow(NotExistException::new);
	}

	@Transactional
	public void removeById(UUID id) {
		checkNull(id, "id 는 null 이 올 수 없습니다");

		if (0 == voucherRepository.deleteById(id)) {
			throw new DeletionFailException();
		}
	}

	@Transactional(readOnly = true)
	void checkDuplicatedId(UUID id) {
		if (voucherRepository.findById(id)
			.isPresent()) {
			throw new AlreadyExistException();
		}
	}

	private void checkNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	private void checkPositive(long numb, String message) {
		if (numb <= 0) {
			throw new IllegalArgumentException(message);
		}
	}
}
