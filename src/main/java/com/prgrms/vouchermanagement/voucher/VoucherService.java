package com.prgrms.vouchermanagement.voucher;

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

	public List<Voucher> findAllVoucher() {
		return voucherRepository.findAllVoucher();
	}

	public Optional<Voucher> publishVoucher(VoucherType voucherType, long voucherDiscountInfo) {
		UUID id = UUID.randomUUID();
		Voucher voucher = null;

		try {
			voucher = voucherType.getVoucher(id, voucherDiscountInfo);

			logger.info("Publish new Voucher : {}", voucher);

			voucherRepository.save(voucher);
		} catch (CreationFailException e) {
			logger.error("{}", e.getMessage(), e);
		}

		return Optional.ofNullable(voucher);
	}

}
