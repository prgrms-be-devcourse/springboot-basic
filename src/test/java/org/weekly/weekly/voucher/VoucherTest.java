package org.weekly.weekly.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.weekly.weekly.util.DiscountMap;
import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.domain.FixedDiscount;
import org.weekly.weekly.voucher.domain.PercentDiscount;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.repository.VoucherRepository;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class VoucherTest {
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setVoucherRepository() {
        voucherRepository = new VoucherRepository();
    }

    @ParameterizedTest
    @CsvSource({
            "100000,12, 1",
            "10,1, 1"
    })
    void 바우처가_이미_존재하면_예외발생(String amount, String expiration, String no)  {
        // Given
        UUID voucherId = UUID.randomUUID();
        LocalDate localDate = LocalDate.now();
        VoucherDto voucherDto = VoucherDto.parseDto(voucherId, amount, localDate, expiration);


        assertThatCode(()-> {
            // when
            Discount discount = DiscountMap.getDiscountMap(no).getCls().getDeclaredConstructor().newInstance();
            voucherRepository.insert(voucherDto.parseWith(discount));

            // then
            assertThat(voucherRepository.findById(voucherId).isPresent()).isTrue();
        }).doesNotThrowAnyException();
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

        @ParameterizedTest
        @CsvSource({
                "10000,1000,9000",
                "16000,1300, 14700"
        })
        void 고정_할인금액_적용하여_결과확인(int userInput, int discountMoney, int result) {
            // Given
            Discount discount = new FixedDiscount();
            LocalDate current = LocalDate.now();
            LocalDate next = current.plusMonths(1);
            Voucher voucher = new Voucher(UUID.randomUUID(), discountMoney, current, next, discount);

            // when
            int afterApply = voucher.applyDiscount(userInput);

            // then
            assertThat(afterApply).isEqualTo(result);
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

        @ParameterizedTest
        @CsvSource({
                "10000,100,0",
                "16000, 50, 8000",
                "1000, 40, 6000"
        })
        void 퍼센트_할인금액_적용하여_결과확인(int userInput, int discountMoney, int result) {
            // Given
            Discount discount = new PercentDiscount();
            LocalDate current = LocalDate.now();
            LocalDate next = current.plusMonths(1);
            Voucher voucher = new Voucher(UUID.randomUUID(), discountMoney, current, next, discount);

            // when
            int afterApply = voucher.applyDiscount(userInput);

            // then
            assertThat(afterApply).isEqualTo(result);
        }
    }
}
