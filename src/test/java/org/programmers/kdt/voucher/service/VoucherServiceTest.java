package org.programmers.kdt.voucher.service;

import org.junit.jupiter.api.*;
import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.programmers.kdt.voucher.factory.VoucherFactory;
import org.programmers.kdt.voucher.repository.FileVoucherRepository;
import org.programmers.kdt.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherServiceTest {
    VoucherService voucherService;
    VoucherRepository voucherRepository;
    VoucherFactory voucherFactory;

    @BeforeAll
    void setUp() {
        voucherRepository = mock(FileVoucherRepository.class);
        voucherFactory = new VoucherFactory();
        voucherService = new VoucherServiceImpl(voucherRepository, voucherFactory);
    }

    @Test
    @Order(1)
    @DisplayName("바우처 생성하기")
    void createVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        when(voucherRepository.insert(voucher)).thenReturn(voucher);

        assertThat(voucherService.createVoucher(VoucherType.FIXED, voucher.getVoucherId(), 1000)).isEqualTo(voucher);
    }

    @Test
    @Order(2)
    @DisplayName("모든 바우처 정보 가져오기")
    void getAllVouchers() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 2000);
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        when(voucherRepository.findAll()).thenReturn(List.of(voucher, voucher2, voucher3));

        assertThat(voucherService.getAllVouchers()).contains(voucher);
        assertThat(voucherService.getAllVouchers()).contains(voucher2);
        assertThat(voucherService.getAllVouchers()).contains(voucher3);
    }

    @Test
    @Order(3)
    @DisplayName("저장된 바우처 가져오기")
    void getVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        // 성공
        assertThat(voucherService.getVoucher(voucher.getVoucherId()).get()).isEqualTo(voucher);

        // 실패
        assertThat(voucherService.getVoucher(UUID.randomUUID())).isNotPresent();
    }

    @Test
    @DisplayName("바우처 사용하기")
    @Disabled
    void useVoucher() {
        // pass
    }

    @Test
    @Order(4)
    @DisplayName("바우처 제거하기")
    void deleteVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        doNothing().when(voucherRepository).deleteVoucher(voucher.getVoucherId());

        voucherService.removeVoucher(voucher.getVoucherId());

        verify(voucherRepository, times(1)).deleteVoucher(voucher.getVoucherId());
    }

}