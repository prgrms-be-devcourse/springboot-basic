package org.prgms.springbootbasic.wallet.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherStatusTest {

	@DisplayName("알맞은 Status가 입력된다면 VoucherStatus를 잘 반환해야 한다")
	@Test
	void CustomerStatus_of_pass_test() {
		//when
		final VoucherStatus availableStatus = VoucherStatus.of("AVAILABLE");
		//then
		assertThat(availableStatus).isNotNull();
		assertThat(availableStatus).isEqualTo(VoucherStatus.AVAILABLE);

		//when
		final VoucherStatus blockedStatus = VoucherStatus.of("USED");
		//then
		assertThat(blockedStatus).isNotNull();
		assertThat(blockedStatus).isEqualTo(VoucherStatus.USED);
	}

	@DisplayName("잘못된 값이 입력된다면 IllegalArgumentException을 반환해야 한다.")
	@Test
	void CustomerStatus_of_fail_test() {
		assertThrows(IllegalArgumentException.class, () -> VoucherStatus.of("adsf"));
		assertThrows(IllegalArgumentException.class, () -> VoucherStatus.of(null));
	}

}
