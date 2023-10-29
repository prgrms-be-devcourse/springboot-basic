package com.programmers.springbasic.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.springbasic.dto.CustomerDto;
import com.programmers.springbasic.dto.VoucherDto;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;
import com.programmers.springbasic.repository.customer.CustomerRepository;
import com.programmers.springbasic.repository.voucher.VoucherRepository;
import com.programmers.springbasic.repository.wallet.WalletRepository;

class VoucherServiceTest {

	@Mock
	private VoucherRepository voucherRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private WalletRepository walletRepository;

	@InjectMocks
	private VoucherService voucherService;

	private UUID voucherId;
	private Voucher voucher1;
	private Voucher voucher2;
	private UUID customerId;
	private Customer customer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		voucherId = UUID.randomUUID();
		voucher1 = new FixedAmountVoucher(voucherId, 100);
		voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10);
		customerId = UUID.randomUUID();
		customer = new Customer(customerId, "홍길동", "hong@example.com", LocalDateTime.now());
	}

	@Test
	void 바우처_목록을_조회한다() {
		when(voucherRepository.findAll()).thenReturn(Arrays.asList(voucher1, voucher2));

		List<VoucherDto> vouchers = voucherService.getVouchers();

		assertThat(vouchers, hasSize(2));
	}

	@Test
	void 바우처를_생성한다() {
		VoucherType voucherType = VoucherType.FIXED_AMOUNT;
		long discountValue = 500;

		VoucherDto result = voucherService.createVoucher(voucherType, discountValue);

		assertThat(result.discountValue(), equalTo(discountValue));
		assertThat(result.voucherType(), equalTo(voucherType));
	}

	@Test
	void 아이디로_바우처를_조회한다() {
		when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher1));

		VoucherDto result = voucherService.getVoucherDetail(voucherId);

		assertThat(result.voucherId(), equalTo(voucherId));
		assertThat(result.discountValue(), equalTo(100L));
		assertThat(result.voucherType(), equalTo(VoucherType.FIXED_AMOUNT));
	}

	@Test
	void 존재하지_않는_바우처를_조회할_경우_예외가_발생한다() {
		UUID voucherId = UUID.randomUUID();
		when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> voucherService.getVoucherDetail(voucherId));
	}

	@Test
	void 바우처를_삭제한다() {
		when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher1));
		doNothing().when(voucherRepository).deleteById(voucherId);

		voucherService.deleteVoucher(voucherId);

		verify(voucherRepository, times(1)).deleteById(voucherId);
	}

	@Test
	void 특정_바우처를_보유한_고객을_조회한다() {
		when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher1));
		when(walletRepository.findCustomerIdsByVoucherId(voucherId)).thenReturn(Collections.singletonList(customerId));
		when(customerRepository.findAllById(Collections.singletonList(customerId))).thenReturn(
			Collections.singletonList(customer));

		List<CustomerDto> customers = voucherService.getCustomersByVoucher(voucherId);

		assertThat(customers, is(notNullValue()));
		assertThat(customers, hasSize(1));
		assertThat(customers.get(0).id(), is(equalTo(customerId)));
	}

	@Test
	void 바우처_세부사항을_변경한다() {
		long newDiscountValue = 50;
		when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher1));

		VoucherDto result = voucherService.updateVoucher(voucherId, newDiscountValue);

		assertThat(result.discountValue(), equalTo(newDiscountValue));
	}

}
