package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.config.CsvProperties;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("csv")
class CsvVoucherRepositoryTest {

    private final String voucherCsvResource = "src/test/resources/csv/voucher/voucher.csv";
    private final String customerCsvResource = "src/test/resources/csv/customer/customer_blacklist.csv";
    private final CsvVoucherRepository voucherRepository;


    public CsvVoucherRepositoryTest() {
        this.voucherRepository = new CsvVoucherRepository(new CsvProperties(voucherCsvResource, customerCsvResource));
    }

    @AfterEach
    void clear() {
        voucherRepository.deleteAllVouchers();
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}, voucherType = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("저장된 바우처 할인값, 바우처 타입 확인 성공")
    void whenSaveCsvVoucherThenSuccessTest(long discountValue, VoucherType voucherType) {
        // given
        Voucher voucher = VoucherFactory.of(discountValue, voucherType);

        // when
        Voucher savedVoucher = voucherRepository.save(voucher);

        // then
        assertThat(savedVoucher.getVoucherType(), is(voucherType));
        assertThat(savedVoucher.getDiscountValue(), is(discountValue));
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}, voucherType = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("다수 바우처 저장 후 조회 성공")
    void whenFindAllVouchersThenSuccessTest(long discountValue, VoucherType voucherType) {
        // given
        final int maxCount = 100;
        Voucher voucher = VoucherFactory.of(discountValue, voucherType);
        for (int count = 0; count < maxCount; count++) {
            voucherRepository.save(voucher);
        }

        // when
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        //then
        assertThat(allVouchers, notNullValue());
        assertThat(allVouchers, hasSize(maxCount));
    }

    static Stream<Arguments> voucherDummy() {
        return Stream.of(
                Arguments.arguments(0L, FIXED_AMOUNT),
                Arguments.arguments(1L, FIXED_AMOUNT),
                Arguments.arguments(10L, FIXED_AMOUNT),
                Arguments.arguments(100L, FIXED_AMOUNT),
                Arguments.arguments(1000L, FIXED_AMOUNT),
                Arguments.arguments(10000L, FIXED_AMOUNT),
                Arguments.arguments(20000L, FIXED_AMOUNT),
                Arguments.arguments(30000L, FIXED_AMOUNT),
                Arguments.arguments(40000L, FIXED_AMOUNT),
                Arguments.arguments(49999L, FIXED_AMOUNT),
                Arguments.arguments(50000L, FIXED_AMOUNT),
                Arguments.arguments(0L, PERCENT_DISCOUNT),
                Arguments.arguments(1L, PERCENT_DISCOUNT),
                Arguments.arguments(10L, PERCENT_DISCOUNT),
                Arguments.arguments(20L, PERCENT_DISCOUNT),
                Arguments.arguments(30L, PERCENT_DISCOUNT),
                Arguments.arguments(40L, PERCENT_DISCOUNT),
                Arguments.arguments(99L, PERCENT_DISCOUNT),
                Arguments.arguments(100L, PERCENT_DISCOUNT)
        );
    }
}