package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherMemoryRepositoryTest {
    private VoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new VoucherMemoryRepository();
    }

    @Test
    @DisplayName("성공: Voucher 던건 저장")
    void save() {
        //given
        Voucher fixedVoucher = createFixedVoucher();

        voucherRepository.save(fixedVoucher);

        //when
        List<Voucher> result = voucherRepository.findAll();

        //then
        assertThat(result).contains(fixedVoucher);
    }

    @Test
    @DisplayName("성공: voucher 단건 조회")
    void findById() {
        //given
        FixedAmountVoucher fixedVoucher = createFixedVoucher();
        voucherRepository.save(fixedVoucher);

        //when
        Optional<Voucher> optionalVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());

        //then
        assertThat(optionalVoucher).isNotEmpty();
        Voucher findVoucher = optionalVoucher.get();
        assertThat(findVoucher).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("성공: voucher 단건 조회 - 존재하지 않은 voucher")
    void findById_ButEmpty() {
        //given
        FixedAmountVoucher fixedVoucher = createFixedVoucher();

        //when
        Optional<Voucher> optionalVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());

        //then
        assertThat(optionalVoucher).isEmpty();
    }

    @Test
    @DisplayName("성공: Voucher 목록 조회")
    void findAll() {
        //given
        Voucher fixedVoucherA = createFixedVoucher();
        Voucher fixedVoucherB = createFixedVoucher();
        voucherRepository.save(fixedVoucherA);
        voucherRepository.save(fixedVoucherB);

        //when
        List<Voucher> result = voucherRepository.findAll();

        //then
        assertThat(result).contains(fixedVoucherA, fixedVoucherB);
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제")
    void deleteById() {
        //given
        Voucher fixedVoucher = createFixedVoucher();
        voucherRepository.save(fixedVoucher);

        //when
        voucherRepository.deleteById(fixedVoucher.getVoucherId());

        //then
        Optional<Voucher> optionalVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());
        assertThat(optionalVoucher).isEmpty();
    }

    @Test
    @DisplayName("예외: voucher 단건 삭제 - 존재하지 않는 voucher")
    void deleteById_ButNoSuchElement() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when
        //then
        assertThatThrownBy(() -> voucherRepository.deleteById(voucherId))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }
}