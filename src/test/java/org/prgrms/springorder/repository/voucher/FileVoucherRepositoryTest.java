package org.prgrms.springorder.repository.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.properties.FileVoucherProperties;

class FileVoucherRepositoryTest {

	private FileVoucherRepository fileVoucherRepository;

	private final String voucherPath = "./src/test/resources/voucher_list.csv";
	private final String blackListPath = "./src/test/resources/customer_blacklist.csv";

	@BeforeEach
	void init() {
		FileVoucherProperties fileVoucherProperties = new FileVoucherProperties(voucherPath);
		fileVoucherRepository = new FileVoucherRepository(fileVoucherProperties);
	}

	@Test
	@DisplayName("바우처를 저장하고, 바우처를 성공적으로 조회한다.")
	void test1() {
		//given
		UUID uuid = UUID.randomUUID();
		Voucher voucher = new PercentDiscountVoucher((uuid), 50, LocalDateTime.now());
		//when
		fileVoucherRepository.save(voucher);
		Optional<Voucher> savedVoucher = fileVoucherRepository.findById(uuid);
		//then
		Assertions.assertTrue(savedVoucher.isPresent());
		Assertions.assertEquals(voucher, savedVoucher.get());
	}

	@Test
	@DisplayName("바우처를 저장하고, 저장한 모든 바우처를 리스트로 성공적으로 가져온다.")
	void test2() {
		//given
		int expectSize = 4;
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				fileVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 50, LocalDateTime.now()));
			} else {
				fileVoucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now()));
			}
		}
		//when
		List<Voucher> savedVouchers = fileVoucherRepository.findAll();
		//then
		Assertions.assertEquals(savedVouchers.size(), expectSize);
	}

	@Test
	@DisplayName("파일에 저장된 바우처를 로컬 저장소에 성공적으로 가져온다.")
	void test3() {
		//given
		int expectSize = 6;
		fileVoucherRepository.load();
		//when
		List<Voucher> vouchers = fileVoucherRepository.findAll();
		//then
		Assertions.assertEquals(expectSize, vouchers.size());
	}

}