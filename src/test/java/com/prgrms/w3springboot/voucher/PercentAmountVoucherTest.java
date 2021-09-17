package com.prgrms.w3springboot.voucher;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.prgrms.w3springboot.order.Order;
import com.prgrms.w3springboot.order.OrderItem;

class PercentAmountVoucherTest {

	@DisplayName("퍼센트 할인 바우처를 생성한다.")
	@Test
	void testCreatePercentVoucher() {
		var customerId = UUID.randomUUID();
		var orderItems = List.of(
			new OrderItem(UUID.randomUUID(), 100L, 1)
		);
		var order = new Order(UUID.randomUUID(), customerId, orderItems);

		Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount is not {0}", order.totalAmount()));
	}

	@DisplayName("퍼센트 할인 금액 적용을 확인한다.")
	@Test
	void testDiscount() {
		long beforeDiscount = 80L;
		Voucher voucher = new PercentAmountVoucher(UUID.randomUUID(), 3, VoucherType.PERCENT);

		long afterDiscount = voucher.discount(beforeDiscount);

		assertThat(afterDiscount).isEqualTo(78L);
	}

	@DisplayName("유효하지 않은 퍼센트 할인 금액이 입력되면 예외가 발생한다.")
	@Test
	void testValidPercent() {
		assertAll(
			() -> assertThatIllegalArgumentException().isThrownBy(
				() -> new PercentAmountVoucher(UUID.randomUUID(), 0, VoucherType.PERCENT)),
			() -> assertThatIllegalArgumentException().isThrownBy(
				() -> new PercentAmountVoucher(UUID.randomUUID(), -20, VoucherType.PERCENT)),
			() -> assertThatIllegalArgumentException().isThrownBy(
				() -> new PercentAmountVoucher(UUID.randomUUID(), 1000, VoucherType.PERCENT))
		);
	}
}