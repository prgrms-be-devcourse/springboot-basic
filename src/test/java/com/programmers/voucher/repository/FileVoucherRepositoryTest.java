package com.programmers.voucher.repository;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.programmers.voucher.AppConfig;
import com.programmers.voucher.domain.FixedDiscountVoucher;
import com.programmers.voucher.domain.Voucher;

class FileVoucherRepositoryTest {

	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	VoucherRepository repository = applicationContext.getBean(FileVoucherRepository.class);

	@Test
	@DisplayName("바우처를 파일에 저장하고 id를 통해 파일에서 바우처를 조회하면 성공한다.")
	void findByUUID() {
		UUID voucherID = UUID.randomUUID();
		Voucher voucher = new FixedDiscountVoucher(voucherID, 1000);

		repository.save(voucher);
		Voucher findVoucher = repository.findByUUID(voucherID);

		Assertions.assertThat(voucher.getVoucherId()).isEqualTo(findVoucher.getVoucherId());
	}
}