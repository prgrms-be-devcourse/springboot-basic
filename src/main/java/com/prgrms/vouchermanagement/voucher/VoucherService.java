package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

	public List<Voucher> findAll() {
		return voucherRepository.findAll();
	}

	public Optional<Voucher> create(VoucherType voucherType, long voucherDiscountInfo) {
		UUID id = UUID.randomUUID();
		Voucher voucher = null;

		try {
			voucher = voucherType.getVoucher(id, voucherDiscountInfo, LocalDateTime.now());

			logger.info("Publish new Voucher : {}", voucher);

			voucherRepository.insert(voucher);
		} catch (CreationFailException e) {
			logger.error("{}", e.getMessage(), e);
		}

		return Optional.ofNullable(voucher);
	}

	public void deleteById(UUID voucherId) {
		// TODO delete 예외상황 처리
		voucherRepository.deleteById(voucherId);
	}

}
