package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("File Voucher Repository Test")
class VoucherFileRepositoryTest {
    private VoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new VoucherFileRepository("VoucherRepositoryTest");
    }

    @AfterEach
    void cleanup() {
        voucherRepository.deleteAll();
    }

    @Test
    void testSaveSuccess() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = 10L;
        Voucher expectedResult = new FixedAmountVoucher(expectedUUID, expectedValue);
        // Act
        Voucher actualResult = voucherRepository.save(expectedResult);
        // Assert
        assertThat(actualResult).isInstanceOf(FixedAmountVoucher.class);
        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(actualResult.getVoucherId()).isEqualTo(expectedUUID);
        assertThat(actualResult.getValue()).isEqualTo(expectedValue);
        assertThat(actualResult.getInformation()).isEqualTo(expectedResult.getInformation());
    }

    @Test
    void testFindAllSuccess() {
        // Arrange
        long expectedValue = 10L;
        Voucher expectedVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), expectedValue);
        Voucher expectedVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), expectedValue);
        List<Voucher> expectedResult = List.of(expectedVoucher1, expectedVoucher2);
        voucherRepository.save(expectedVoucher1);
        voucherRepository.save(expectedVoucher2);
        // Act
        List<Voucher> actualResult = voucherRepository.findAll();
        // Assert
        assertThat(actualResult.size()).isEqualTo(expectedResult.size());
    }
}