package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MemoryVoucherRepositoryTest {

    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    @DisplayName("id로 Voucher를 조회한다.")
    public void findById() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10L);
        voucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        Voucher voucher = optionalVoucher.get();

        //then
        assertThat(voucher).isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("일치하는 id가 없으면 Optional.empty()가 돌아온다")
    public void findByIdWihEmpty() throws Exception{
        // When
        Optional<Voucher> actual = voucherRepository.findById(UUID.randomUUID());

        // Then
        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("현재 메모리에 저장되어있는 Voucher 리스트를 받아온다")
    public void findAll() throws Exception {
        //given
        UUID voucherId1 = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId1, 10L);
        UUID voucherId2 = UUID.randomUUID();
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId2, 10L);
        UUID voucherId3 = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(voucherId3, 10L);

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(fixedAmountVoucher1);
        voucherRepository.save(percentDiscountVoucher);

        //when
        List<Voucher> actual = voucherRepository.findAll();

        //then
        assertThat(actual).contains(fixedAmountVoucher, fixedAmountVoucher1, percentDiscountVoucher);
    }

    @Test
    @DisplayName("리스트에 아무것도 없으면 빈 리스트가 반환된다")
    public void findAllWithEmpty() throws Exception{
        // When
        List<Voucher> actual = voucherRepository.findAll();

        // Then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("저장하면 저장한 객체를 반환한다")
    public void save() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10L);

        //when
        Voucher actual = voucherRepository.save(fixedAmountVoucher);

        //then
        assertThat(actual).isEqualTo(fixedAmountVoucher);
    }
}