package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.voucher.repository.MemoryVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @DisplayName("voucher 생성 테스트 - stub")
    @Test
    public void createVoucherTest() throws Exception {
        // given
        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService stub = new VoucherService(voucherRepository);

        // when
        VoucherEntity voucherEntity = stub.createVoucher(1, 1234L);

        // then
        voucherRepository.findAll();
        assertThat(voucherRepository.findById(voucherEntity.getVoucherId()).isEmpty(), is(false));
        Optional<VoucherEntity> savedVoucher = voucherRepository.findById(voucherEntity.getVoucherId());
        assertThat(voucherEntity.getVoucherId(), is(savedVoucher.get().getVoucherId()));
        assertThat(voucherEntity.getDiscountAmount(), is(1234L));
    }

    @DisplayName("voucher 생성 테스트 - mock")
    @Test
    public void createVoucherTestByMock() throws Exception {
        // given
        MemoryVoucherRepository voucherRepository = mock(MemoryVoucherRepository.class);

        VoucherService stub = new VoucherService(voucherRepository);
        // when
        VoucherEntity voucherEntity = stub.createVoucher(1, 1234);

        // 로직상 setup을 여기서 수행.
        when(voucherRepository.findById(voucherEntity.getVoucherId())).thenReturn(Optional.of(voucherEntity));

        // then
        Optional<VoucherEntity> savedVoucher = voucherRepository.findById(voucherEntity.getVoucherId());
        assertThat(voucherEntity.getVoucherId(), is(savedVoucher.get().getVoucherId()));

        InOrder inOrder = inOrder(voucherRepository);
        inOrder.verify(voucherRepository).insert(voucherEntity);
    }

}