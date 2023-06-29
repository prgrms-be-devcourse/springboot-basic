package org.prgrms.kdt.util;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class LoaderTest {
    public String TestVoucherFilePath = "src/main/resources/TestVoucher.csv";
    public String TestBlackListFilePath = "src/main/resources/test_customer_blacklist.csv";

    @Test
    public void 파일에_바우처_내용_저장_후_저장확인() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID());
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID());
        Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
        voucherMap.put(voucher1.getVoucherId(), voucher1);
        voucherMap.put(voucher2.getVoucherId(), voucher2);
        voucherMap.put(voucher3.getVoucherId(), voucher3);

        Loader.saveMemoryVoucherToFile(voucherMap, TestVoucherFilePath);
        Map<UUID, Voucher> findVoucherMap = Loader.loadFileToMemoryVoucher(TestVoucherFilePath);

        assertThat(findVoucherMap.size(), is(3));
    }

    @Test
    public void 파일에서_메모리로_로드_테스트() {
        Map<UUID, Member> findMemberMap = Loader.loadFileToMemoryMember(TestBlackListFilePath);

        assertThat(findMemberMap.size(), is(3));
    }

}