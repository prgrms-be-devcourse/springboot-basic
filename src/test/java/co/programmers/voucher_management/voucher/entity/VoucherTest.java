package co.programmers.voucher_management.voucher.entity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTest {
	@Test
	@DisplayName("할인 방식에 대한 객체와 아이디가 주어지면 바우처가 생성된다.")
	void createVoucherObject() {
		DiscountStrategy discountStrategy = mock(DiscountStrategy.class);
		long id = 230703104;

		Voucher voucher = new Voucher(id, discountStrategy);
		assertThat(voucher, notNullValue());
		assertThat(voucher.getId(), is(equalTo(id)));
		assertThat(voucher.getDiscountStrategy(), is(equalTo(discountStrategy)));
	}

}
