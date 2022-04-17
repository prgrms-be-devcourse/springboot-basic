package org.programmers.springbootbasic.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.service.VoucherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository = mock(VoucherRepository.class);

    @InjectMocks
    VoucherService voucherService = new VoucherService(voucherRepository);

    @Test
    @DisplayName("유효하지 않은 바우처 넘버로 바우처를 생성할 수 없다.")
    void createVoucherByInvalidVoucherTypeTest(){
        assertThatThrownBy(() -> VoucherType.findByNumber(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 바우처 넘버입니다.");
    }

    @Test
    @DisplayName("바우처를 생성할 수 있다.")
    void createVoucherTest() {
        VoucherType voucherType = VoucherType.FIXED;
        long value = 100L;
        var voucherCreatedByType = voucherType.create(value);

        when(voucherRepository.insert(any())).thenReturn(voucherCreatedByType);

        Voucher voucher = voucherService.createVoucher(voucherType, value);

        assertThat(voucher.getVoucherId()).isEqualTo(voucherCreatedByType.getVoucherId());
    }

    @Test
    @DisplayName("바우처 아이디로 원하는 바우처를 조회할 수 있다.")
    void getVoucherByIdTest() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000L);
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        Voucher retrievedVoucher = voucherService.getVoucher(voucher.getVoucherId());

        assertThat(retrievedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        verify(voucherRepository).findById(voucher.getVoucherId());
    }

    @DisplayName("존재하지 않는 아이디로는 바우처를 조회할 수 없다.")
    @Test
    void getVoucherByNoIdTest() {
        assertThatThrownBy(() -> voucherService.getVoucher(UUID.randomUUID()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("바우처를 찾을 수 없습니다");
    }

    @Test
    @DisplayName("바우처 리스트를 조회할 수 있다.")
    void listVoucherTest() {
        doReturn(voucherList()).when(voucherRepository).findAll();

        final List<Voucher> voucherList = voucherService.getVoucherList();

        assertThat(voucherList).hasSize(6);
        verify(voucherRepository).findAll();
    }

    private List<Voucher> voucherList() {
        final List<Voucher> voucherList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), 1000L * (i + 1)));
            voucherList.add(new PercentDiscountVoucher(UUID.randomUUID(), 10L * (i + 1)));
        }
        return voucherList;
    }
}
