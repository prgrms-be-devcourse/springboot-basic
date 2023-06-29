package org.prgrms.kdt.util;

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

class LoaderTest {
    public String TestVoucherFilePath = "src/main/resources/TestVoucher.csv";
    public String TestBlackListFilePath = "src/main/resources/test_customer_blacklist.csv";

    @Test
    void 파일에_바우처_내용_저장_후_저장확인() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID());
        Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
        voucherMap.put(voucher1.getVoucherId(), voucher1);
        voucherMap.put(voucher2.getVoucherId(), voucher2);

        //when
        Loader.saveMemoryVoucherToFile(voucherMap, TestVoucherFilePath);
        List<Voucher> foundVouchers = Loader.loadFileToMemoryVoucher(TestVoucherFilePath);

        //then
        assertThat(foundVouchers.size(), is(2));
    }

    @Test
    void 파일에_블랙멤버_저장_후_저장확인() {
        //given
        Member blackMember1 = new Member(UUID.randomUUID(), "haha", MemberStatus.BLACK);
        Member blackMember2 = new Member(UUID.randomUUID(), "hoho", MemberStatus.BLACK);
        Map<UUID, Member> memberMap = new ConcurrentHashMap<>();
        memberMap.put(blackMember1.getMemberId(), blackMember1);
        memberMap.put(blackMember2.getMemberId(), blackMember2);

        //when
        Loader.saveMemoryMemberToFile(memberMap, TestBlackListFilePath);
        List<Member> foundMembers = Loader.loadFileToMemoryMember(TestBlackListFilePath);

        //then
        assertThat(foundMembers.size(), is(2));
    }

}