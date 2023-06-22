package com.devcourse.springbootbasic.voucher;

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
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
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
}
