package org.weekly.weekly.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.request.VoucherInfoRequest;
import org.weekly.weekly.voucher.repository.VoucherRepository;

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
    @CsvSource(value = {
            "100000,12: 1",
            "10,1: 1"
    }, delimiter = ':')
    void 바우처가_이미_존재하면_예외발생(String userInput, String no)  {
        // Given
        UUID voucherId = UUID.randomUUID();
        VoucherInfoRequest voucherInfo = VoucherInfoRequest.of(userInput);
        DiscountType discount = DiscountType.getDiscountTypeByNumber(no);
        LocalDate now = LocalDate.now();
        Voucher voucher = Voucher.of(voucherId, voucherInfo.getAmount(), now, voucherInfo.getExpiration(), discount);

        // when
        voucherRepository.insert(voucher);

        // then
        assertThat(voucherRepository.findById(voucherId).isPresent()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "10, -123",
            "10, -1",
            "10, 0"
    })
    void 바우처_발행시간이_유효시간보다_느리면_예외발생(String userInput) {
        // Given
//        UUID voucherId = UUID.randomUUID();
//        LocalDate localDate = LocalDate.now();
        DiscountType discount = DiscountType.FIXED;
        VoucherInfoRequest voucherInfo = VoucherInfoRequest.of(userInput);

        VoucherCreationRequest request = new VoucherCreationRequest(voucherInfo, discount);

        // when + then
        assertThatThrownBy(()->request.toVoucher())
                .isInstanceOf(RuntimeException.class);
    }

    @Nested
    class 고정바우처_테스트 {
        @ParameterizedTest
        @ValueSource(strings = {
                "-1,12",
                " -50, 1"
        })
        void 바우처_금액이_자연수가_아니면_예외발생(String userInput) {
            // Given
            DiscountType discountType = DiscountType.FIXED;

            VoucherInfoRequest voucherInfoRequest =  VoucherInfoRequest.of(userInput);

            // when
            VoucherCreationRequest request = new VoucherCreationRequest(voucherInfoRequest, discountType);

            // then
            assertThatThrownBy(()-> request.toVoucher())
                    .isInstanceOf(RuntimeException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "10000,1000,9000",
                "16000,1300, 14700"
        })
        void 고정_할인금액_적용하여_결과확인(int userInput, int discountMoney, int result) {
            // Given
            LocalDate current = LocalDate.now();
            Voucher voucher = Voucher.of(UUID.randomUUID(), discountMoney, current, 1, DiscountType.FIXED);

            // when
            long afterApply = voucher.applyDiscount(userInput);

            // then
            assertThat(afterApply).isEqualTo(result);
        }
    }

    @Nested
    class 퍼센트바우처_테스트 {
        @ParameterizedTest
        @ValueSource(strings = {
                "101,12",
                "-1, 1"
        })
        void 바우처_퍼센트값이_자연수가_아니면_예외발생(String userInput) {
            // Given
            UUID voucherId = UUID.randomUUID();
            LocalDate now = LocalDate.now();
            VoucherInfoRequest voucherInfo = VoucherInfoRequest.of(userInput);


            // when + then
            assertThatThrownBy(()->Voucher.of(voucherId, voucherInfo.getAmount(), now, 1, DiscountType.PERCENT))
                    .isInstanceOf(RuntimeException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "10000,100,0",
                "16000, 50, 8000",
                "1000, 40, 600"
        })
        void 퍼센트_할인금액_적용하여_결과확인(int userInput, int discountMoney, int result) {
            // Given
            LocalDate current = LocalDate.now();
            Voucher voucher = Voucher.of(UUID.randomUUID(), discountMoney, current, 1, DiscountType.PERCENT);

            // when
            long afterApply = voucher.applyDiscount(userInput);

            // then
            assertThat(afterApply).isEqualTo(result);
        }
    }
}
