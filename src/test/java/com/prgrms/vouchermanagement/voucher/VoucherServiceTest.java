package com.prgrms.vouchermanagement.voucher;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.vouchermanagement.commons.exception.CreationFailException;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

	private VoucherType fixedType;
	private VoucherType percentType;

	@InjectMocks
	private VoucherService voucherService;

	@Mock
	private VoucherRepository voucherRepository;

	@BeforeEach
	public void setup() {
		fixedType = VoucherType.FIXED;
		percentType = VoucherType.PERCENT;
	}

	@Test
	@DisplayName("고정할인 금액이 양수인 바우처를 정상방급하여 null 이 아닌 Optional 반환")
	public void Given_fixedAmount_When_createFixedAmountVoucher_thenCreationSuccess() {
		long amount = 1000L;

		Voucher newVoucher = voucherService.create(fixedType, amount);

		assertThat(newVoucher.getDiscountInfo(), is(amount));
		assertThat(newVoucher.getType(), is(VoucherType.FIXED));
	}

	@Test
	@DisplayName("할인 퍼센트가 음수인 바우처 생성을 시도 할 때 CreationFailException 이 발생")
	public void Given_percentAmount_When_createPercentVoucher_thenCreationFail() {
		long amount = -1000L;

		Assertions.assertThrows(CreationFailException.class, () ->
			voucherService.create(percentType, amount));
	}

	@Test
	@DisplayName("할인 퍼센트가 0원인 바우처 생성을 시도 할 때 CreationFailException 이 발생")
	public void Given_percentAmountZero_When_createPercentVoucher_thenCreationFail() {
		long amount = 0L;

		Assertions.assertThrows(CreationFailException.class, () ->
			voucherService.create(percentType, amount));
	}

	@Test
	@DisplayName("할인 퍼센트가 양수인 바우처 발급을 시도 할 때, 정상적으로 생성된다")
	public void Given_percentAmount_When_createPercentVoucher_thenCreationSuccess() {
		long amount = 10L;

		Voucher newVoucher = voucherService.create(percentType, amount);

		assertThat(newVoucher.getDiscountInfo(), is(amount));
		assertThat(newVoucher.getType(), is(VoucherType.PERCENT));
	}

	@Test
	@DisplayName("고정할인 금액이 음수인 바우처 생성을 시도 할 때 CreationFailException 이 발생")
	public void Given_fixedAmount_When_createFixedAmountVoucher_thenCreationFail() {
		long amount = -1000L;

		Assertions.assertThrows(CreationFailException.class, () ->
			voucherService.create(fixedType, amount));
	}

	@Test
	@DisplayName("고정할인 금액이 0원인 바우처 생성을 시도 할 때 CreationFailException 이 발생")
	public void Given_fixedAmountZero_When_createFixedAmountVoucher_thenCreationFail() {
		long amount = 0L;

		Assertions.assertThrows(CreationFailException.class, () ->
			voucherService.create(fixedType, amount));
	}
}