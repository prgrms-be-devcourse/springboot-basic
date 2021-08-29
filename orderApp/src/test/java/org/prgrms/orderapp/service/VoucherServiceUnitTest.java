package org.prgrms.orderapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.orderapp.model.FixedAmountVoucher;
import org.prgrms.orderapp.model.PercentDiscountVoucher;
import org.prgrms.orderapp.model.Voucher;
import org.prgrms.orderapp.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

class VoucherServiceUnitTest {

    @Test
    @DisplayName("VoucherService는 FixedAmountVoucher 타입에 따라 Voucher를 생성할 수 있다.")
    void testCreateFixedAmountVoucher() {
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        Optional<Voucher> voucher = voucherService.createVoucher("fixed", "100");

        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getClass(), is(fixedAmountVoucher.getClass()));
        assertThat(voucher.get().getAmount(), is(fixedAmountVoucher.getAmount()));
    }

    @Test
    @DisplayName("VoucherService는 PercentDiscountVoucher 타입에 따라 Voucher를 생성할 수 있다.")
    void testCreatePercentDiscountVoucher() {
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        Optional<Voucher> voucher = voucherService.createVoucher("percent", "30");

        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getClass(), is(percentDiscountVoucher.getClass()));
        assertThat(voucher.get().getAmount(), is(percentDiscountVoucher.getAmount()));
    }

    @Test
    @DisplayName("VoucherService는 올바르지 않은 입력에는 Optional.empty를 리턴한다.")
    void testInvalidVoucher() {
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository);

        Optional<Voucher> voucher = voucherService.createVoucher("invalidType", "30");
        assertThat(voucher.isEmpty(), is(true));

        voucher = voucherService.createVoucher("fixed", "-10");
        assertThat(voucher.isEmpty(), is(true));

        voucher = voucherService.createVoucher("percent", "130");
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("VoucherService는 voucherId로 Voucher를 가져올 수 있다.")
    void testGetVoucher() {
        UUID id = UUID.randomUUID();
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        FixedAmountVoucher voucher = new FixedAmountVoucher(id, 1000);
        when(voucherRepository.findById(id)).thenReturn(Optional.ofNullable(voucher));
        VoucherService voucherService = new VoucherService(voucherRepository);

        Voucher retrievedVoucher = voucherService.getVoucher(id);

        assertThat(retrievedVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("VoucherService는 존재하지 않는 voucherId로 조회시 에러를 발생시킨다.")
    void testGetVoucherWithInvalidId() {
        UUID id = UUID.randomUUID();
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        when(voucherRepository.findById(id)).thenReturn(Optional.empty());
        VoucherService voucherService = new VoucherService(voucherRepository);

        assertThrows(RuntimeException.class, ()-> voucherService.getVoucher(id));
    }

    @Test
    @DisplayName("VoucherService는 바우처를 저장할 수 있습니다.")
    void testSaveVoucher() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        when(voucherRepository.save(voucher)).thenReturn(voucher);
        VoucherService voucherService = new VoucherService(voucherRepository);

        Voucher retrievedVoucher = voucherService.saveVoucher(voucher);

        assertThat(retrievedVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("VoucherService는 모든 바우처를 가져올 수 있습니다.")
    void testGetAllVoucher() {
        List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new PercentDiscountVoucher(UUID.randomUUID(), 30),
                new FixedAmountVoucher(UUID.randomUUID(), 400)
        );
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        when(voucherRepository.findAll()).thenReturn(vouchers);
        VoucherService voucherService = new VoucherService(voucherRepository);

        List<Voucher> retrievedVouchers = voucherService.getAllVoucher();

        assertThat(retrievedVouchers, hasSize(vouchers.size()));
        assertThat(retrievedVouchers, containsInAnyOrder(vouchers.toArray()));
    }
}