package org.weekly.weekly.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoucherTest {
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setVoucherRepository() {
        voucherRepository = new VoucherRepository();
    }

    @ParameterizedTest
    @CsvSource({
            "1,100000,12",
            "2, 10, 1"
    })
    void 바우처가_이미_존재하면_예외발생(int voucherId, int amount, int expiration) {
        // Given
        LocalDate localDate = LocalDate.now();
        Voucher voucher = new FixedDiscountVoucher(voucherId, amount, localDate, localDate.plusMonths(expiration));
        voucherRepository.insert(voucher);

        // when + then
        assertThat(voucherRepository.findById(voucherId).isEmpty()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1,100000,12",
            "2, 10, 1"
    })
    void 바우처_발행시간이_유효시간보다_느리면_예외발생(int voucherId, int amount, int expiration) {
        // Given
        LocalDate localDate = LocalDate.now();
        LocalDate expirationDate = localDate.minusMonths(expiration);

        // when + then
        assertThatThrownBy(()->new FixedDiscountVoucher(voucherId, amount, localDate, expirationDate))
                .isInstanceOf(RuntimeException.class);
    }

    @Nested
    class 고정바우처_테스트() {
        @ParameterizedTest
        @CsvSource({
                "1,-1,12",
                "2, 0, 1"
        })
        void 바우처_금액이_자연수가_아니면_예외발생(int voucherId, int amount, int expiration) {
            // Given
            LocalDate localDate = LocalDate.now();
            LocalDate expirationDate = localDate.plusMonths(expiration);

            // when + then
            assertThatThrownBy(()->new FixedDiscountVoucher(voucherId, amount, localDate, expirationDate))
                    .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    class 퍼센트바우처_테스트() {


        @ParameterizedTest
        @CsvSource({
                "1,101,12",
                "2, -1, 1"
        })
        void 바우처_퍼센트값이_자연수가_아니면_예외발생(int voucherId, int percent, int expiration) {
            // Given
            LocalDate localDate = LocalDate.now();
            LocalDate expirationDate = localDate.plusMonths(expiration);

            // when + then
            assertThatThrownBy(()->new PercentDiscountVoucher(voucherId, percent, localDate, expirationDate))
                    .isInstanceOf(RuntimeException.class);
        }
    }
}
