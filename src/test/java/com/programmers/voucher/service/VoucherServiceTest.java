package com.programmers.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.programmers.voucher.AppConfig;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.voucher.VoucherType;
import com.programmers.voucher.repository.voucher.VoucherRepository;

class VoucherServiceTest {

	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	VoucherRepository repository = applicationContext.getBean(VoucherRepository.class);
	VoucherService service = applicationContext.getBean(VoucherService.class);

	@Test
	@DisplayName("바우처를 생성하면 생성과 저장이 성공한다.")
	void createVoucher() {
		String fixedType = VoucherType.FIXED.getType();
		String percentType = VoucherType.PERCENT.getType();
		int fixedAmount = 1000;
		int percentAmount = 10;

		Voucher fixedVoucher = service.createVoucher(fixedType, fixedAmount);
		Voucher percentVoucher = service.createVoucher(percentType, percentAmount);
		Voucher findFixed = repository.findByUUID(fixedVoucher.getVoucherId());
		Voucher findPercent = repository.findByUUID(percentVoucher.getVoucherId());

		Assertions.assertThat(fixedVoucher.toString()).isEqualTo(findFixed.toString());
		Assertions.assertThat(percentVoucher.toString()).isEqualTo(findPercent.toString());
	}
}