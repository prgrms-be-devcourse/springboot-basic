package org.weekly.weekly.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.weekly.weekly.voucher.domain.FixedDiscount;
import org.weekly.weekly.voucher.domain.PercentDiscount;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.repository.VoucherRepository;

import java.time.LocalDate;
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
            "100000,12",
            "10,1"
    })
    void 바우처가_이미_존재하면_예외발생(String amount, String expiration) {
        // Given
        UUID voucherId = UUID.randomUUID();
        LocalDate localDate = LocalDate.now();
        VoucherDto voucherDto = VoucherDto.parseDto(voucherId, amount, localDate, expiration);


        voucherRepository.insert(voucherDto.parse());

        // when + then
        assertThat(voucherRepository.findById(voucherId).isPresent()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "10, -123",
            "10, -1",
            "10, 0"
    })
    void 바우처_발행시간이_유효시간보다_느리면_예외발생(String amount, String expiration) {
        // Given
        UUID voucherId = UUID.randomUUID();
        LocalDate localDate = LocalDate.now();

        // when + then
        assertThatThrownBy(()->VoucherDto.parseDto(voucherId, amount, localDate, expiration))
                .isInstanceOf(RuntimeException.class);
    }

    @Nested
    class 고정바우처_테스트 {
        @ParameterizedTest
        @CsvSource({
                "-1,12",
                " 0, 1"
        })
        void 바우처_금액이_자연수가_아니면_예외발생(String amount, String expiration) {
            // Given
            UUID voucherId = UUID.randomUUID();
            LocalDate localDate = LocalDate.now();

            // when + then
            assertThatThrownBy(()->VoucherDto.parseDto(voucherId, amount, localDate, expiration))
                    .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    class 퍼센트바우처_테스트 {
        @ParameterizedTest
        @CsvSource({
                "101,12",
                "-1, 1"
        })
        void 바우처_퍼센트값이_자연수가_아니면_예외발생(String percent, String expiration) {
            // Given
            UUID voucherId = UUID.randomUUID();
            LocalDate localDate = LocalDate.now();

            // when + then
            assertThatThrownBy(()->VoucherDto.parseDto(voucherId, percent, localDate, expiration))
                    .isInstanceOf(RuntimeException.class);
        }
    }
}
