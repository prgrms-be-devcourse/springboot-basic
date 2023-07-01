package org.prgrms.kdt.service;

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
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.FixedAmount;
import org.prgrms.kdt.model.PercentAmount;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.InMemoryVoucherRepository;
import org.prgrms.kdt.model.repository.VoucherRepository;

class VoucherEntityServiceTest {

	private static Stream<Arguments> voucherDTOProvider() {
		List<VoucherDTO> voucherDTOS1 = Arrays.asList(
			new VoucherDTO(5L, new FixedAmount(100), VoucherType.FixedAmountVoucher),
			new VoucherDTO(6L, new PercentAmount(50), VoucherType.PercentDiscountVoucher),
			new VoucherDTO(7L, new FixedAmount(200), VoucherType.FixedAmountVoucher)
		);

		List<VoucherDTO> voucherDTOS2 = Arrays.asList(
			new VoucherDTO(5L, new FixedAmount(100), VoucherType.FixedAmountVoucher),
			new VoucherDTO(2L, new PercentAmount(50), VoucherType.PercentDiscountVoucher),
			new VoucherDTO(4L, new PercentAmount(40), VoucherType.PercentDiscountVoucher)
		);

		List<VoucherDTO> voucherDTOS3 = Arrays.asList(
			new VoucherDTO(3L, new PercentAmount(50), VoucherType.PercentDiscountVoucher),
			new VoucherDTO(2L, new FixedAmount(50), VoucherType.FixedAmountVoucher),
			new VoucherDTO(1L, new PercentAmount(100), VoucherType.PercentDiscountVoucher)
		);

		return Stream.of(
			Arguments.of(voucherDTOS1),
			Arguments.of(voucherDTOS2),
			Arguments.of(voucherDTOS3)
		);
	}

	@ParameterizedTest
	@DisplayName("바우처 서비스의 저장 및 불러오기 테스트")
	@MethodSource("voucherDTOProvider")
	void createVoucherTest(List<VoucherDTO> voucherDTOs) {
		// given
		Map<Long, VoucherEntity> store = new HashMap<>();
		VoucherRepository voucherRepository = new InMemoryVoucherRepository(store);
		VoucherService voucherService = new VoucherService(voucherRepository);

		//when
		voucherDTOs.stream()
			.forEach(voucher -> voucherService.createVoucher(voucher));
		List<VoucherDTO> expectedVoucherDTOs = voucherService.getVouchers()
			.stream()
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(voucherDTOs)
			.containsExactlyInAnyOrderElementsOf(expectedVoucherDTOs);
	}
}