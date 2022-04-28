package org.programmers.kdt.weekly.voucher.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.programmers.kdt.weekly.voucher.VoucherDto;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
    private final VoucherService voucherService = new VoucherService(voucherRepository);
    private final UUID VOUCHER_ID = UUID.randomUUID();
    private final VoucherType VOUCHER_TYPE = VoucherType.FIXED_AMOUNT_VOUCHER;
    private final long VALUE = 20L;
    private final Voucher VOUCHER = VOUCHER_TYPE.create(new VoucherDto(VOUCHER_ID,VALUE, LocalDateTime.now()));

    @Test
    @DisplayName("create 호출시 voucherRepository.insert() 가 호출되고 voucher가 return 되어야함")
    void create(){
        //given
        given(voucherRepository.insert(VOUCHER)).willReturn(VOUCHER);
        //when
        var createVoucher = voucherService.create(VOUCHER_ID,VOUCHER_TYPE,VALUE);
        //then
        verify(voucherRepository,times(1)).insert(VOUCHER);
        assertThat(createVoucher, equalTo(VOUCHER));
    }

    @Test
    @DisplayName("getVouchers 호출시 voucherRepository.findAll() 이 호출되고 voucherList가 return 되어야함")
    void getVouchers() {
        //given
        var vouchers = List.of(VOUCHER);
        given(voucherRepository.findAll()).willReturn(vouchers);
        //when
        var findVouchers = voucherService.getVouchers();
        //then
        verify(voucherRepository, times(1)).findAll();
        assertThat(findVouchers, equalTo(vouchers));
    }
}