package org.prgms.springbootbasic.customer.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerStatusTest {

	@DisplayName("알맞은 Status가 입력된다면 CustomerStatus를 잘 반환해야 한다")
	@Test
	void CustomerStatus_of_pass_test() {
		//when
		final CustomerStatus availableStatus = CustomerStatus.of("AVAILABLE");
		//then
		assertThat(availableStatus).isNotNull();
		assertThat(availableStatus).isEqualTo(CustomerStatus.AVAILABLE);

		//when
		final CustomerStatus blockedStatus = CustomerStatus.of("BLOCKED");
		//then
		assertThat(blockedStatus).isNotNull();
		assertThat(blockedStatus).isEqualTo(CustomerStatus.BLOCKED);
	}

	@DisplayName("잘못된 값이 입력된다면 IllegalArgumentException을 반환해야 한다.")
	@Test
	void CustomerStatus_of_fail_test() {
		assertThrows(IllegalArgumentException.class, () -> CustomerStatus.of("adsf"));
		assertThrows(IllegalArgumentException.class, () -> CustomerStatus.of(null));
	}
}
