package com.programmers.order.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

import com.programmers.order.domain.FixedAmountVoucher;
import com.programmers.order.domain.PercentDiscountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.utils.FileUtils;

@Profile("file")
class FileStoreManagerTest {
	private FileVoucherRepository storeManager = new FileVoucherRepository();

	private static String FILE_NAME = FileUtils.getWorkingDirectory() + "/" + FileUtils.generateCsvFileName("voucher");

	@DisplayName("모든 테스트가 끝난후 호출되는 파일을 삭제하는 테스트")
	@AfterAll
	static void afterFileDeleteTest() {
		String workingDirectory = FileUtils.getWorkingDirectory();
		System.out.println("workingDirectory = " + workingDirectory);
		new File(FILE_NAME).delete();
	}

	@DisplayName("쿠폰 생성 테스트")
	@Test
	void saveVoucherTest() throws IOException {
		//given
		List<Voucher> demoVouchers = getDemoData();
		AtomicInteger index = new AtomicInteger();

		demoVouchers.forEach(voucher -> {
			//when
			Voucher savedVoucher = storeManager.saveVoucher(voucher);

			// then
			Assertions.assertThat(savedVoucher).isNotNull();
			Assertions.assertThat(savedVoucher.getVoucherId()).isEqualTo(demoVouchers.get(index.get()).getVoucherId());
			index.getAndIncrement();
		});

	}

	@DisplayName("쿠폰 리스트 가져오기")
	@Test
	void getVouchersTest() {
		//given
		List<UUID> demoData = getDemoData().stream()
				.map(Voucher::getVoucherId)
				.toList();
		//when
		List<Voucher> vouchers = storeManager.getVouchers();
		List<UUID> uuids = vouchers.stream()
				.map(Voucher::getVoucherId)
				.toList();

		//then
		assertThat(vouchers.size(), is(10));

	}

	public List<Voucher> getDemoData() {
		return IntStream.rangeClosed(1, 10)
				.boxed()
				.map(val -> {
					if (val < 5) {
						return new FixedAmountVoucher(UUID.randomUUID(), val);
					}

					return new PercentDiscountVoucher(UUID.randomUUID(), val);
				}).toList();
	}
}