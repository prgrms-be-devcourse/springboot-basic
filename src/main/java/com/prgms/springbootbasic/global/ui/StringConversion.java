package com.prgms.springbootbasic.global.ui;

import com.prgms.springbootbasic.member.model.Member;
import com.prgms.springbootbasic.voucher.model.Voucher;
import com.prgms.springbootbasic.voucher.util.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class StringConversion {

    private static final String FORMAT_FIXED = "voucher type : %s voucher Id : %s amount : %d";
    private static final String FORMAT_PERCENT = "voucher type : %s voucher Id : %s percent : %d";
    private static final String FORMAT_MEMBER = "member Id : %s name : %s";

    public List<String> changeMemberToString(List<Member> members) {
        return members.stream()
                .map(this::changeMemberToString)
                .toList();
    }

    public List<String> changeVoucherToString(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(this::changeVoucherToString)
                .toList();
    }

    private String changeMemberToString(Member member) {
        UUID memberId = member.getMemberId();
        String name = member.getName();
        return String.format(FORMAT_MEMBER, memberId, name);
    }

    private String changeVoucherToString(Voucher voucher) {
        VoucherType voucherType = voucher.getVoucherType();
        UUID voucherId = voucher.getVoucherId();
        Long number = voucher.getNumber();
        if (voucherType == VoucherType.FIXED) return String.format(FORMAT_FIXED, voucherType, voucherId, number);
        return String.format(FORMAT_PERCENT, voucherType, voucherId, number);
    }

}
