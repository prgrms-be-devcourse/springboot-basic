package com.devcourse.springbootbasic.voucher;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.service.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("default")
public class VoucherFileTest {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private YamlProperties yamlProperties;

    static Stream<Arguments> provideVoucher() {
        List<Voucher> list = List.of(
                VoucherType.FIXED_AMOUNT.getVoucherFactory().create(10),
                VoucherType.PERCENT_DISCOUNT.getVoucherFactory().create(5)
        );
        return Stream.of(
                Arguments.of(list.get(0), list.get(0).toString()),
                Arguments.of(list.get(1), list.get(1).toString())
        );
    }

    @ParameterizedTest
    @MethodSource("provideVoucher")
    void insertVoucherToFileTest(Voucher input, String expect) throws IOException {
        Path path = Path.of(yamlProperties.getVoucherRecordPath());
        long prevLineCount = Files.lines(path)
                .count();
        voucherService.createVoucher(input);
        long postLineCount = Files.lines(path)
                .count();
        Assertions.assertEquals(prevLineCount + 1, postLineCount);
    }

}
