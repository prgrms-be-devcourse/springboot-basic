package org.prgrms.kdt.model.repository.inmemory;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InMemoryVoucherRepositoryTest {
	private VoucherRepository voucherRepository;
	@BeforeAll
	public void setUp() {
		Map<Long, VoucherEntity> store = new HashMap<>();
		voucherRepository = new InMemoryVoucherRepository(store);
	}

	private static Stream<Arguments> vouchersProvider() {
		List<VoucherEntity> vouchers1 = Arrays.asList(
			new VoucherEntity(5L, 100, "FixedAmountVoucher"),
			new VoucherEntity(6L, 10, "PercentDiscountVoucher"),
			new VoucherEntity(7L, 50, "FixedAmountVoucher")
		);

		return Stream.of(
			Arguments.of(vouchers1)
		);
	}

	@ParameterizedTest
	@DisplayName("인메모리에서 voucher를 저장하고 불러올 수 있다.")
	@MethodSource("vouchersProvider")
	void 바우처_저장_불러오기_테스트(List<VoucherEntity> voucherEntities) {
		//when
		voucherEntities.forEach(voucher -> voucherRepository.saveVoucher(voucher));
		List<VoucherEntity> expectedVoucherEntities = voucherRepository.findAllEntities();

		//then
		assertThat(voucherEntities.size(), is(expectedVoucherEntities.size()));
	}
	@DisplayName("인메모리에서 voucher를 불러오고 삭제할 수 있다.")
	@Test
	void 바우처_삭제_테스트() {
		// given
		List<VoucherEntity> voucherEntitiesBeforeDeleted = voucherRepository.findAllEntities();
		VoucherEntity targetVoucher = voucherEntitiesBeforeDeleted.get(0);

		// when
		voucherRepository.deleteVoucherById(targetVoucher.getVoucherId());

		// then
		List<VoucherEntity> voucherEntitiesAfterDeleted = voucherRepository.findAllEntities();
		assertThat(voucherEntitiesAfterDeleted.size(), is(voucherEntitiesBeforeDeleted.size()-1));
	}

	@DisplayName("인메모리에서 voucher를 불러오고 찾을 수 있다.")
	@Test
	void 바우처_찾기_테스트() {
		// given
		List<VoucherEntity> targetVouchers = voucherRepository.findAllEntities();
		VoucherEntity targetVoucher = targetVouchers.get(0);

		// when
		Optional<VoucherEntity> optionalVoucherEntity = voucherRepository.findVoucherById(targetVoucher.getVoucherId());

		// then
		assertThat(optionalVoucherEntity.isEmpty(), is(false));
		VoucherEntity foundVoucher = optionalVoucherEntity.get();
		assertThat(foundVoucher.getVoucherId(), is(targetVoucher.getVoucherId()));
	}
}