package com.programmers.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.config.AppConfig;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.repository.VoucherRepository;

class VoucherServiceTest {

	AppConfig appConfig = new AppConfig();
	VoucherRepository repository = appConfig.voucherRepository();
	VoucherService service = appConfig.voucherService();

	@Test
	@DisplayName("바우처를 생성하면 생성과 저장이 성공한다.")
	void createVoucher() {
		String fixedType = "fixed";
		String percentType = "percent";
		int fixedAmount = 1000;
		int percentAmount = 10;

		Voucher fixedVoucher = service.createVoucher(fixedType, fixedAmount);
		Voucher percentVoucher = service.createVoucher(percentType, percentAmount);
		Voucher findFixed = repository.findByUUID(fixedVoucher.getVoucherId());
		Voucher findPercent = repository.findByUUID(percentVoucher.getVoucherId());

		Assertions.assertThat(fixedVoucher).isEqualTo(findFixed);
		Assertions.assertThat(percentVoucher).isEqualTo(findPercent);
	}
}