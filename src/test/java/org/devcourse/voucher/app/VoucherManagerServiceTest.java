package org.devcourse.voucher.app;

import org.devcourse.voucher.domain.voucher.Voucher;
import org.devcourse.voucher.domain.voucher.VoucherType;
import org.devcourse.voucher.domain.voucher.policy.FixedDiscountPolicy;
import org.devcourse.voucher.domain.voucher.policy.PercentDiscountPolicy;
import org.devcourse.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class VoucherManagerServiceTest {

    @Test
    @DisplayName("바우처 저장에 성공한다")
    public void successSaveVoucher () {
        //given
        Voucher voucher = new Voucher(0, VoucherType.FIX,1000);
        Voucher voucherWithId = new Voucher(1, VoucherType.FIX,1000);
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        given(voucherRepository.save(voucher)).willReturn(voucherWithId);

        VoucherService service = new VoucherManagerService(voucherRepository);

        //when
        Voucher savedVoucher = service.save(voucher);

        //then
        assertThat(savedVoucher).isEqualTo(voucherWithId);
        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    @DisplayName("바우처 저장에 실패한다")
    public void failSaveVoucher () {
        //given
        Voucher voucher = null;
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService service = new VoucherManagerService(voucherRepository);

        //when & then
        assertThatThrownBy(() -> service.save(voucher))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("저장하고자 하는 바우처가 빈 값일 수 없습니다");
        verify(voucherRepository, times(0)).save(voucher);
    }

    @Test
    @DisplayName("저장된 모든 바우처를 불러온다")
    public void loadAllVoucher () {
        //given
        List<Voucher> vouchers = List.of(
                new Voucher(1, VoucherType.FIX,1000),
                new Voucher(2, VoucherType.PERCENT,10),
                new Voucher(3, VoucherType.PERCENT,20),
                new Voucher(4, VoucherType.FIX,1000)
        );

        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        given(voucherRepository.findAll()).willReturn(vouchers);
        VoucherService service = new VoucherManagerService(voucherRepository);

        //when
        List<Voucher> findVouchers = service.findAll();

        assertThat(findVouchers.size()).isEqualTo(4);
        verify(voucherRepository, times(1)).findAll();
    }
}
