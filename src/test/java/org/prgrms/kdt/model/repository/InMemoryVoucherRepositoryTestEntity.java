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
import org.prgrms.kdt.model.entity.FixedDiscountVoucherEntity;
import org.prgrms.kdt.model.entity.PercentDiscountVoucherEntity;
import org.prgrms.kdt.model.entity.VoucherEntity;

class InMemoryVoucherRepositoryTestEntity {

	private static Stream<Arguments> vouchersProvider() {
		List<VoucherEntity> vouchers1 = Arrays.asList(
			new FixedDiscountVoucherEntity(5L, new FixedAmount(100)),
			new PercentDiscountVoucherEntity(6L, new FixedAmount(50)),
			new FixedDiscountVoucherEntity(7L, new FixedAmount(200))
		);

		List<VoucherEntity> vouchers2 = Arrays.asList(
			new FixedDiscountVoucherEntity(5L, new FixedAmount(100)),
			new PercentDiscountVoucherEntity(2L, new FixedAmount(50)),
			new PercentDiscountVoucherEntity(4L, new FixedAmount(40))
		);

		List<VoucherEntity> vouchers3 = Arrays.asList(
			new PercentDiscountVoucherEntity(3L, new FixedAmount(100)),
			new FixedDiscountVoucherEntity(2L, new FixedAmount(50)),
			new FixedDiscountVoucherEntity(1L, new FixedAmount(200))
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
	void createVoucherTest(List<VoucherEntity> voucherEntities) {
		// given
		Map<Long, VoucherEntity> store = new HashMap<>();
		VoucherRepository voucherRepository = new InMemoryVoucherRepository(store);

		//when
		voucherEntities.stream()
			.forEach(voucher -> voucherRepository.createVoucher(voucher));
		List<Long> expectedIds = voucherRepository.readAll()
			.stream()
			.map(VoucherEntity::getVoucherId)
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(voucherEntities)
			.flatExtracting(VoucherEntity::getVoucherId)
			.containsExactlyInAnyOrderElementsOf(expectedIds);
	}
}