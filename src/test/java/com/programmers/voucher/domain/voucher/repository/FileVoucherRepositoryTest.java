package com.programmers.voucher.domain.voucher.repository;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;

@SpringBootTest
class FileVoucherRepositoryTest {

	@Autowired
	VoucherRepository repository;

	@AfterEach
	void afterEach() {
		repository.clear();
	}

	@Test
	@DisplayName("바우처를 파일에 저장하고 id를 통해 파일에서 바우처를 조회하면 성공한다.")
	void findByUUID() {
		UUID voucherID = UUID.randomUUID();
		Voucher voucher = new Voucher(voucherID, VoucherType.FIXED, "1000");

		repository.save(voucher);
		Voucher findVoucher = repository.findByUUID(voucherID);

		Assertions.assertThat(findVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
	}
}