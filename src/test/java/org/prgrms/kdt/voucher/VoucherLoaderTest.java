package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
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
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID());
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