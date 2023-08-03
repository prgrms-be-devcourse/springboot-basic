package org.prgrms.kdt.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.model.repository.inmemory.InMemoryVoucherRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherEntityServiceTest {

	private Map<Long, VoucherEntity> store;
	private VoucherRepository voucherRepository;
	private VoucherService voucherService;

	@BeforeAll
	public void setUp() {
		store = new HashMap<>();
		voucherRepository = new InMemoryVoucherRepository(store);
		voucherService = new VoucherService(voucherRepository);
	}

	private static Stream<Arguments> voucherDTOProvider() {
		List<VoucherRequest> voucherDTOS1 = Arrays.asList(
			new VoucherRequest(100, "FixedAmountVoucher"),
			new VoucherRequest(50, "PercentDiscountVoucher"),
			new VoucherRequest(200, "FixedAmountVoucher")
		);

		return Stream.of(
			Arguments.of(voucherDTOS1)
		);
	}

	@ParameterizedTest
	@DisplayName("바우처 서비스에서 바우처를 저장하고 불러올 수 있다.")
	@MethodSource("voucherDTOProvider")
	void 바우처_저장_불러오기_테스트(List<VoucherRequest> voucherRequests) {
		//when
		voucherRequests.forEach(voucherService::saveVoucher);
		List<VoucherResponse> expectedVouchers = voucherService.getVouchers();

		//then
		assertThat(voucherRequests.size(), is(expectedVouchers.size()));
	}

	@DisplayName("바우처 서비스에서 바우처를 불러오고 삭제할 수 있다.")
	void 바우처_삭제_테스트() {
		// given
		List<VoucherResponse> voucherResponsesBeforeDeleted = voucherService.getVouchers();
		VoucherResponse targetVoucher = voucherResponsesBeforeDeleted.get(0);

		//when
		voucherRepository.deleteVoucherById(targetVoucher.voucherId());

		//then
		List<VoucherResponse> voucherResponsesAfterDeleted = voucherService.getVouchers();
		assertThat(voucherResponsesAfterDeleted.size(), is(voucherResponsesBeforeDeleted.size()-1));
	}

	@DisplayName("바우처 서비스에서 바우처를 불러오고 찾을 수 있다.")
	void 바우처_찾기_테스트() {
		// given
		List<VoucherResponse> voucherResponsesBeforeDeleted = voucherService.getVouchers();
		VoucherResponse targetVoucher = voucherResponsesBeforeDeleted.get(0);

		// when
		VoucherResponse foundVoucher = voucherService.findVoucherById(targetVoucher.voucherId());

		// then
		assertThat(foundVoucher.voucherId(), is(targetVoucher.voucherId()));
	}
}