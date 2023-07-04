package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.io.CsvReader;
import com.devcourse.springbootbasic.application.io.CsvWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("default")
class FileVoucherRepositoryTest {

    FileVoucherRepository voucherRepository;

    String filepath = "src/main/resources/voucher_record.csv";

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"))),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "2")))
        );
    }

    @BeforeEach
    void init() {
        voucherRepository = new FileVoucherRepository(new CsvReader(), new CsvWriter());
        voucherRepository.setFilepath(filepath);
    }

    @ParameterizedTest
    @DisplayName("바우처 생성 성공하면 바우처 반환함")
    @MethodSource("provideVouchers")
    void testInsert(Voucher voucher) {
        var result = voucherRepository.insert(voucher);
        var file = new File(filepath);
        assertThat(result, instanceOf(Voucher.class));
        assertThat(file, notNullValue());
    }

    @Test
    @DisplayName("바우처 리스트 반환 시 성공")
    void testFindAll() {
        var result = voucherRepository.findAll();
        assertThat(result, notNullValue());
        assertThat(result.size(), is(greaterThanOrEqualTo(0)));
        if (result.size() > 0) {
            assertThat(result.get(0), instanceOf(Voucher.class));
        }
    }
}