package org.prgrms.kdt.voucher.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherLoaderTest {
    VoucherLoader voucherLoader;

    @BeforeEach
    void setup() {
        voucherLoader = new VoucherLoader("src/test/TestVoucher.csv");
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("메모리의 바우처들 파일에 저장 후 저장된 파일 확인")
    void saveMemoryVoucherToFile(Voucher voucher1, Voucher voucher2) {
        //given
        Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
        voucherMap.put(voucher1.getVoucherId(), voucher1);
        voucherMap.put(voucher2.getVoucherId(), voucher2);

        //when
        voucherLoader.saveMemoryVoucherToFile(voucherMap);
        Map<UUID, Voucher> foundVouchers = voucherLoader.loadFileToMemoryVoucher();

        //then
        assertThat(foundVouchers.size(), is(2));
    }

    static Stream<Voucher[]> voucherSource() {
        Voucher voucher1 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Voucher voucher2 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        return Stream.of(new Voucher[][]{{voucher1, voucher2}});
    }
}