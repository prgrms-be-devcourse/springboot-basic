package org.programmers.VoucherManagement.voucher.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.programmers.VoucherManagement.voucher.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 1. save
 * 2. find All
 */
public class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new MemoryVoucherRepository();
    }


    @Test
    @DisplayName("바우처를 메모리에 저장하는 테스트 - 성공")
    void save_Voucher_Success() {
        //given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(1000));
        Voucher percentVoucher = new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(10));

        //when
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);
        Optional<Voucher> fixedVoucherExpect = voucherRepository.findById(fixedVoucher.getVoucherId());
        Optional<Voucher> percentVoucherExpect = voucherRepository.findById(percentVoucher.getVoucherId());

        //then
        assertThat(fixedVoucherExpect).isEqualTo(fixedVoucher);
        assertThat(percentVoucherExpect).isEqualTo(percentVoucher);
    }

    @ParameterizedTest
    @DisplayName("메모리에 저장된 바우처를 모두 조회하는 테스트 - 성공")
    @MethodSource("저장된바우처를_모두조회_성공()")
    void findAll_Success(List<Voucher> voucherList) {
        //given
        voucherList.stream().forEach(v -> voucherRepository.save(v));

        //when
        List<Voucher> voucherListExpect = voucherRepository.findAll();

        //then
        assertThat(voucherListExpect).isEqualTo(voucherList);
    }


    private static Stream<Arguments> 저장된바우처를_모두조회_성공() {
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(1000)));
        voucherList.add(new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(10)));
        voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(2000)));
        voucherList.add(new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(20)));
        return Stream.of(Arguments.of(voucherList));
    }

}
