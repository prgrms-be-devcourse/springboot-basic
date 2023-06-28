package org.prgrms.kdt.model.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.model.FixedAmount;
import org.prgrms.kdt.model.entity.FixedDiscountVoucher;
import org.prgrms.kdt.model.entity.PercentDiscountVoucher;
import org.prgrms.kdt.model.entity.Voucher;

class InMemoryVoucherRepositoryTest {

	private static Stream<Arguments> vouchersProvider() {
		List<Voucher> vouchers1 = Arrays.asList(
			new FixedDiscountVoucher(5L, new FixedAmount(100)),
			new PercentDiscountVoucher(6L, new FixedAmount(50)),
			new FixedDiscountVoucher(7L, new FixedAmount(200))
		);

		List<Voucher> vouchers2 = Arrays.asList(
			new FixedDiscountVoucher(5L, new FixedAmount(100)),
			new PercentDiscountVoucher(2L, new FixedAmount(50)),
			new PercentDiscountVoucher(4L, new FixedAmount(40))
		);

		List<Voucher> vouchers3 = Arrays.asList(
			new PercentDiscountVoucher(3L, new FixedAmount(100)),
			new FixedDiscountVoucher(2L, new FixedAmount(50)),
			new FixedDiscountVoucher(1L, new FixedAmount(200))
		);

		return Stream.of(
			Arguments.of(vouchers1),
			Arguments.of(vouchers2),
			Arguments.of(vouchers3)
		);
	}

	@ParameterizedTest
	@DisplayName("인메모리 repository 저장 및 불러오기 테스트")
	@MethodSource("vouchersProvider")
	void createVoucherTest(List<Voucher> vouchers) {
		// given
		Map<Long, Voucher> store = new HashMap<>();
		VoucherRepository voucherRepository = new InMemoryVoucherRepository(store);

		//when
		vouchers.stream()
			.forEach(voucher -> voucherRepository.createVoucher(voucher));
		List<Long> expectedIds = voucherRepository.readAll()
			.stream()
			.map(Voucher::getVoucherId)
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(vouchers)
			.flatExtracting(Voucher::getVoucherId)
			.containsExactlyInAnyOrderElementsOf(expectedIds);
	}
}