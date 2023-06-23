package com.devcourse.springbootbasic.list;

import com.devcourse.springbootbasic.engine.Platform;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.io.OutputConsole;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.FixedAmountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.PercentDiscountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.repository.MemoryVoucherRepository;
import com.devcourse.springbootbasic.engine.voucher.repository.VoucherRepository;
import com.devcourse.springbootbasic.engine.voucher.service.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
public class ListTester {

    @Autowired
    private InputConsole inputConsole;
    @Autowired
    private OutputConsole outputConsole;

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

    @Test
    void blackCustomerListTest() throws IOException {
        List<String> blackCustomers = inputConsole.getBlackCustomers();
        Assertions.assertDoesNotThrow(() -> outputConsole.printBlackCustomers(blackCustomers));
    }
}
