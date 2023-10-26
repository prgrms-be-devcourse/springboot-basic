package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository;
    FixedDiscountVoucher fixedDiscountVoucher;
    PercentDiscountVoucher percentDiscountVoucher;

    @BeforeEach
    void setUp(){
        voucherRepository = new MemoryVoucherRepository();
        fixedDiscountVoucher = new FixedDiscountVoucher(10L);
        percentDiscountVoucher = new PercentDiscountVoucher(10L);
    }

    @AfterEach
    void cleanUp(){
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void saveVoucher_Success_Test() {
        // Given

        // When
        Voucher savedVoucher = voucherRepository.save(fixedDiscountVoucher);

        // Then
        assertThat(savedVoucher.getVoucherId(), is(fixedDiscountVoucher.getVoucherId()));
        assertThat(savedVoucher.getDiscount(), is(fixedDiscountVoucher.getDiscount()));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void findAllVouchers_Success_Test() {
        // Given
        voucherRepository.save(fixedDiscountVoucher);
        voucherRepository.save(percentDiscountVoucher);

        // When
        List<Voucher> vouchers = voucherRepository.findAll();

        // Then
        assertThat(vouchers, containsInAnyOrder(fixedDiscountVoucher, percentDiscountVoucher));
    }

    @Test
    @DisplayName("아이디로 바우처 조회 테스트")
    void findVoucherById_Success_Test() {
        // Given
        voucherRepository.save(fixedDiscountVoucher);

        // When
        Optional<Voucher> findVoucher = voucherRepository.findById(fixedDiscountVoucher.getVoucherId());

        // Then
        assertThat(findVoucher.isPresent(), is(true));
        assertThat(findVoucher.get(), equalTo(fixedDiscountVoucher));
    }

    @Test
    @DisplayName("존재하지 않는 바우처 아이디 Optional Empty 반환 테스트")
    void findVoucherById_NotExistId_Failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();

        // When
        Optional<Voucher> findVoucher = voucherRepository.findById(notExistId);

        // Then
        assertThat(findVoucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("아이디로 바우처 삭제 테스트")
    void deleteById_Success_Test() {
        // Given
        voucherRepository.save(fixedDiscountVoucher);

        // When
        voucherRepository.deleteById(fixedDiscountVoucher.getVoucherId());

        // Then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 전체 삭제 테스트")
    void deleteAllVouchers_Success_Test() {
        // Given
        voucherRepository.save(fixedDiscountVoucher);
        voucherRepository.save(percentDiscountVoucher);

        // When
        voucherRepository.deleteAll();

        // Then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 업데이트 테스트")
    void updateVoucher_Success_Test() {
        // Given
        voucherRepository.save(fixedDiscountVoucher);
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(20L, UseStatusType.UNAVAILABLE);

        // When
        voucherRepository.update(fixedDiscountVoucher.getVoucherId(), voucherUpdateRequest);

        // Then
        assertThat(fixedDiscountVoucher.getDiscount(), is(voucherUpdateRequest.getDiscount()));
        assertThat(fixedDiscountVoucher.getUseStatusType(), is(voucherUpdateRequest.getUseStatusType()));
    }
}
