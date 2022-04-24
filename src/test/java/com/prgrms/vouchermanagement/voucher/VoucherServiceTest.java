package com.prgrms.vouchermanagement.voucher;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
		// given
		long amount = 1000L;
		// when
		Optional<Voucher> voucherOptional = voucherService.publishVoucher(fixedType, amount);
		// then
		assertThat(voucherOptional.isPresent(), is(true));
	}

	@Test
	@DisplayName("할인 퍼센트가 음수인 바우처 생성결과 null 을 담은 Optional 반환")
	public void Given_percentAmount_When_createPercentVoucher_thenCreationFail() {
		// given
		long amount = -1000L;
		// when
		Optional<Voucher> voucherOptional = voucherService.publishVoucher(percentType, amount);
		// then
		Assertions.assertThrows(NoSuchElementException.class, () -> voucherOptional.get());
	}

	@Test
	@DisplayName("할인 퍼센트가 0원인 바우처 생성결과 null 을 담은 Optional 반환")
	public void Given_percentAmountZero_When_createPercentVoucher_thenCreationFail() {
		// given
		long amount = 0L;
		// when
		Optional<Voucher> voucherOptional = voucherService.publishVoucher(percentType, amount);
		// then
		Assertions.assertThrows(NoSuchElementException.class, () -> voucherOptional.get());
	}

	@Test
	@DisplayName("할인 퍼센트가 양수인 바우처를 정상방급하여 null 이 아닌 Optional 반환")
	public void Given_percentAmount_When_createPercentVoucher_thenCreationSuccess() {
		// given
		long amount = 10L;
		// when
		Optional<Voucher> voucherOptional = voucherService.publishVoucher(percentType, amount);
		// then
		assertThat(voucherOptional.isPresent(), is(true));
	}

	@Test
	@DisplayName("고정할인 금액이 음수인 바우처 생성결과 null 을 담은 Optional 반환")
	public void Given_fixedAmount_When_createFixedAmountVoucher_thenCreationFail() {
		// given
		long amount = -1000L;
		// when
		Optional<Voucher> voucherOptional = voucherService.publishVoucher(fixedType, amount);
		// then
		Assertions.assertThrows(NoSuchElementException.class, () -> voucherOptional.get());
	}

	@Test
	@DisplayName("고정할인 금액이 0원인 바우처 생성결과 null 을 담은 Optional 반환")
	public void Given_fixedAmountZero_When_createFixedAmountVoucher_thenCreationFail() {
		// given
		long amount = 0L;
		// when
		Optional<Voucher> voucherOptional = voucherService.publishVoucher(fixedType, amount);
		// then
		Assertions.assertThrows(NoSuchElementException.class, () -> voucherOptional.get());
	}

}