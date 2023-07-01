package org.prgrms.kdt.util;

import java.util.Arrays;
import java.util.List;
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

class VoucherMapperTest {

	@ParameterizedTest
	@DisplayName("VoucherEntity에서 VoucherDTO로 변경하는 메서드 테스트")
	@MethodSource("voucherListProvider")
	void toVoucherDTOTest(List<VoucherDTO> expectedVoucherDTOs, List<VoucherEntity> voucherEntities) {
		//when
		List<VoucherDTO> voucherDTOS = voucherEntities.stream()
			.map(VoucherMapper::toVoucherDTO)
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(voucherDTOS)
			.containsExactlyInAnyOrderElementsOf(expectedVoucherDTOs);
	}

	@ParameterizedTest
	@DisplayName("VoucherDTO에서 VoucherEntity로 변경하는 메서드 테스트")
	@MethodSource("voucherListProvider")
	void toVoucherEntityTest(List<VoucherDTO> voucherDTOs, List<VoucherEntity> expectedVoucherEntities) {
		//when
		List<VoucherEntity> voucherEntities = voucherDTOs.stream()
			.map(VoucherMapper::toVoucherEntity)
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(voucherEntities)
			.containsExactlyInAnyOrderElementsOf(expectedVoucherEntities);
	}

	private static Stream<Arguments> voucherListProvider() {
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
			new VoucherDTO(3L, new PercentAmount(100), VoucherType.PercentDiscountVoucher),
			new VoucherDTO(2L, new FixedAmount(50), VoucherType.FixedAmountVoucher),
			new VoucherDTO(1L, new FixedAmount(200), VoucherType.FixedAmountVoucher)
		);

		List<VoucherEntity> voucherEntities1 = Arrays.asList(
			new VoucherEntity(5L, 100, VoucherType.FixedAmountVoucher),
			new VoucherEntity(6L, 50, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(7L, 200, VoucherType.FixedAmountVoucher)
		);

		List<VoucherEntity> voucherEntities2 = Arrays.asList(
			new VoucherEntity(5L, 100, VoucherType.FixedAmountVoucher),
			new VoucherEntity(2L, 50, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(4L, 40, VoucherType.PercentDiscountVoucher)
		);

		List<VoucherEntity> voucherEntities3 = Arrays.asList(
			new VoucherEntity(3L, 100, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(2L, 50, VoucherType.FixedAmountVoucher),
			new VoucherEntity(1L, 200, VoucherType.FixedAmountVoucher)
		);

		return Stream.of(
			Arguments.of(voucherDTOS1, voucherEntities1),
			Arguments.of(voucherDTOS2, voucherEntities2),
			Arguments.of(voucherDTOS3, voucherEntities3)
		);
	}

}