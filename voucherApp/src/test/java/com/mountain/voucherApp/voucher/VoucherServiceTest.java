package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.repository.MemoryVoucherRepository;
import com.mountain.voucherApp.repository.VoucherRepository;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mountain.voucherApp.utils.DiscountPolicyUtil.*;
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
        Voucher voucher = stub.createVoucher(1, 1234);

        // then
        assertThat(voucherRepository.findById(voucher.getVoucherId()).isEmpty(), is(false));
        Optional<Voucher> savedVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(voucher.getVoucherId(), is(savedVoucher.get().getVoucherId()));
        assertThat(voucher.getAmount(), is(1234L));
    }

    @DisplayName("voucher 생성 테스트 - mock")
    @Test
    public void createVoucherTestByMock() throws Exception {
        // given
        MemoryVoucherRepository voucherRepository = mock(MemoryVoucherRepository.class);

        VoucherService stub = new VoucherService(voucherRepository);
        // when
        Voucher voucher = stub.createVoucher(1, 1234);

        // 로직상 setup을 여기서 수행.
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        // then
        Optional<Voucher> savedVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(voucher.getVoucherId(), is(savedVoucher.get().getVoucherId()));

        InOrder inOrder = inOrder(voucherRepository);
        inOrder.verify(voucherRepository).insert(voucher);
    }

}