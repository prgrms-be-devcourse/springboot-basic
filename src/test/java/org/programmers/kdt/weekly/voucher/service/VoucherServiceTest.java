package org.programmers.kdt.weekly.voucher.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

	private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
	private final VoucherService voucherService = new VoucherService(voucherRepository);
	private final UUID VOUCHER_ID = UUID.randomUUID();
	private final VoucherType VOUCHER_TYPE = VoucherType.FIXED;
	private final int VALUE = 20;
	private final Voucher VOUCHER = VOUCHER_TYPE.create(VOUCHER_ID, VALUE);

	@Test
	@DisplayName("create 호출시 voucherRepository.insert() 가 호출되고 voucher가 return 되어야함")
	void create() {
		//given
		when(voucherRepository.insert(VOUCHER)).thenReturn(VOUCHER);
		//when
		var createVoucher = voucherService.save(VOUCHER_TYPE, VALUE);
		//then
		verify(voucherRepository, times(1)).insert(VOUCHER);
		assertThat(createVoucher, equalTo(VOUCHER));
	}

	@Test
	@DisplayName("create 호출시 voucherRepository.insert() 가 호출되고 voucher가 return 되어야함")
	void createFail() {
		//given
		when(voucherRepository.insert(VOUCHER)).thenReturn(VOUCHER);
		//when

		var createVoucher = voucherService.save(VOUCHER_TYPE, VALUE);
		//then
		verify(voucherRepository, times(1)).insert(VOUCHER);
		assertThat(createVoucher, equalTo(VOUCHER));
	}

	@Test
	@DisplayName("getVouchers 호출시 voucherRepository.findAll() 이 호출되고 voucherList가 return 되어야함")
	void getVouchers() {
		//given
		var vouchers = List.of(VOUCHER);
		when(voucherRepository.findAll()).thenReturn(vouchers);
		//when
		var findVouchers = voucherService.getVouchers();
		//then
		verify(voucherRepository, times(1)).findAll();
		assertThat(findVouchers, equalTo(vouchers));
	}
}