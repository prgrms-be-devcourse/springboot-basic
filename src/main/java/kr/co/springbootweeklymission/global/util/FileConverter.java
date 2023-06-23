package kr.co.springbootweeklymission.global.util;

import kr.co.springbootweeklymission.domain.member.entity.Member;
import kr.co.springbootweeklymission.domain.model.MemberStatus;
import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.domain.voucher.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.domain.voucher.entity.Voucher;

import java.util.StringTokenizer;
import java.util.UUID;

public class FileConverter {
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String DELIMINATOR = " ,";

    public static String toVoucherString(VoucherResDTO.FILE file) {
        return file.getVoucherId() + SPACE + file.getAmount() + SPACE + file.getVoucherPolicy() + NEW_LINE;
    }

    public static Voucher toVoucher(String file) {
        StringTokenizer tokens = new StringTokenizer(file, DELIMINATOR);

        return Voucher.builder()
                .voucherId(UUID.fromString(tokens.nextToken()))
                .amount(Integer.parseInt(tokens.nextToken()))
                .voucherPolicy(VoucherPolicy.valueOf(tokens.nextToken()))
                .build();
    }

    public static Member toMember(String file) {
        StringTokenizer tokens = new StringTokenizer(file, DELIMINATOR);

        return Member.builder()
                .memberId(UUID.fromString(tokens.nextToken()))
                .memberStatus(MemberStatus.valueOf(tokens.nextToken()))
                .build();
    }
}
