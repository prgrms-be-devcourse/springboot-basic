package org.programmers.springbootbasic.controller.members;

import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.dataOf;

public record MemberDto(Long memberId,
                        String name,
                        String email,
                        Timestamp lastLoginAt,
                        Timestamp singedUpAt,
                        List<Voucher> vouchers) {

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(getString("회원 등록 번호", this.memberId, Long.class));
        stringBuilder.append(getString("이름", this.name, String.class));
        stringBuilder.append(getString("이메일", this.email, String.class));
        stringBuilder.append(getString("최종 로그인 일시", this.lastLoginAt, Timestamp.class));
        stringBuilder.append(getString("가입 일시", this.singedUpAt, Timestamp.class));
        stringBuilder.append(getString("바우처 목록", this.vouchers, List.class));
        return stringBuilder.toString();
    }

    private <T> String getString(String dataName, T data, Class<T> dataType) {
        if (memberId == null) {
            return "";
        }
        if (dataType == List.class) {
            getVoucherInformation();
        }
        return dataName + ": " + data + "\n";
    }

    private String getVoucherInformation() {
        if (this.vouchers.isEmpty()) {
            return "비어 있음";
        }

        List<String> allVouchersInformation = new ArrayList<>(this.vouchers.size());

        //TODO: Iterable 출력 로직 분리, 재사용: Drawer
        for (Voucher voucher : this.vouchers) {
            allVouchersInformation.add(dataOf(voucher));
        }

        var voucherDataStringBuilder = new StringBuilder();
        int sequence = 0;
        for (String eachVoucherInformation : allVouchersInformation) {
            voucherDataStringBuilder.append(++sequence);
            voucherDataStringBuilder.append(". ");
            voucherDataStringBuilder.append(eachVoucherInformation);
            voucherDataStringBuilder.append("\n");
        }

        return voucherDataStringBuilder.toString();
    }
}