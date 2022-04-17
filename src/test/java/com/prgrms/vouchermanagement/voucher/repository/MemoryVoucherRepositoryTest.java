package com.prgrms.vouchermanagement.voucher.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

class MemoryVoucherRepositoryTest {
	private VoucherRepository voucherRepository;

	@BeforeEach
	public void setUp(){
		voucherRepository = new MemoryVoucherRepository();
	}

	@Test
	@DisplayName("실제로 저장했던 데이터 개수와 같은 사이즈여야한다")
	void given_repository_when_compareSize_thenSuccess(){

	}

	@Test
	@DisplayName("실제로 저장했던 데이터를 모두 포함하고 있어야한다")
	void given_repositoryAndRealData_When_checkAllDataContained_thenSuccess() {

	}

	@Test
	@DisplayName("데이터를 저장하고 찾아올 수 있어야 한다")
	void given_repositoryAndData_When_saveData_thenFindingThatDataSuccess() {
		// given
		UUID id = UUID.randomUUID();
		Voucher voucher = VoucherType.FIXED.getVoucher(id, 1000L);
		// when
		voucherRepository.save(voucher);
		// then
		assertThat(voucherRepository.findById(id),
			is(voucher));
	}
}