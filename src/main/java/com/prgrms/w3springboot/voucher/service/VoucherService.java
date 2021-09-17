package com.prgrms.w3springboot.voucher.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherFactory;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
	private final VoucherRepository voucherRepository;
	private final VoucherFactory voucherFactory;

	public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
		this.voucherRepository = voucherRepository;
		this.voucherFactory = voucherFactory;
	}

	public Voucher getVoucher(UUID voucherId) {
		return voucherRepository.findById(voucherId)
			.orElseThrow(
				() -> new NullPointerException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
	}

	public Voucher createVoucher(UUID voucherId, long discountAmount, VoucherType voucherType) {
		Voucher voucher = voucherFactory.createVoucher(voucherId, discountAmount, voucherType);
		return voucherRepository.insert(voucher);
	}

    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

	// 나중에 구현
	public void useVoucher(Voucher voucher) {

	}
}
