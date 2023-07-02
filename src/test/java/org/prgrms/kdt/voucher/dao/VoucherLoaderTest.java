package org.prgrms.kdt.voucher.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherLoaderTest {
    VoucherLoader voucherLoader;

    @BeforeEach
    void setup() {
        voucherLoader = new VoucherLoader("src/test/TestVoucher.csv");
    }

    @Test
    void 파일에_바우처_내용_저장_후_저장확인() {
        //given
        Voucher voucher1 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Voucher voucher2 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
        voucherMap.put(voucher1.getVoucherId(), voucher1);
        voucherMap.put(voucher2.getVoucherId(), voucher2);

        //when
        voucherLoader.saveMemoryVoucherToFile(voucherMap);
        Map<UUID, Voucher> foundVouchers = voucherLoader.loadFileToMemoryVoucher();

        //then
        assertThat(foundVouchers.size(), is(2));
    }
}