package com.devcourse.springbootbasic.voucher;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.FixedAmountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.PercentDiscountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.repository.MemoryVoucherRepository;
import com.devcourse.springbootbasic.engine.voucher.repository.VoucherRepository;
import com.devcourse.springbootbasic.engine.voucher.service.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VoucherTest {

    public static Stream<Arguments> provideVoucherMap() {
        List<UUID> list = IntStream.range(0, 3)
                .mapToObj(i -> UUID.randomUUID())
                .toList();
        return Stream.of(
                Arguments.of(
                        Map.of(
                            list.get(0), new FixedAmountVoucher(list.get(0), VoucherType.FIXED_AMOUNT, 10),
                            list.get(1), new FixedAmountVoucher(list.get(1), VoucherType.FIXED_AMOUNT, 20),
                            list.get(2), new PercentDiscountVoucher(list.get(2), VoucherType.PERCENT_DISCOUNT, 1)
                        ),
                        3
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideVoucherMap")
    public void voucherListTest(Map<UUID, Voucher> input, int expected) {
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        voucherRepository.setVoucherMap(input);
        VoucherService voucherService = new VoucherService(voucherRepository);
        Assertions.assertEquals(expected, voucherService.getAllVouchers().size());
    }

    public static Stream<Arguments> provideVoucherType() {
        return Stream.of(
                Arguments.of("1", VoucherType.FIXED_AMOUNT),
                Arguments.of("2", VoucherType.PERCENT_DISCOUNT)
        );
    }

    @ParameterizedTest
    @MethodSource("provideVoucherType")
    void voucherTypeTest(String input, VoucherType expected) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputConsole inputConsole = new InputConsole();
        VoucherType voucherType = inputConsole.inputVoucherType();
        Assertions.assertEquals(expected, voucherType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "-1"})
    void voucherTypeExceptionTest(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputConsole inputConsole = new InputConsole();
        Assertions.assertThrows(InvalidDataException.class, inputConsole::inputVoucherType);
    }
}
