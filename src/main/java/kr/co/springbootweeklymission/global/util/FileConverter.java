package kr.co.springbootweeklymission.global.util;

import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.domain.voucher.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.domain.voucher.entity.Voucher;

import java.util.StringTokenizer;
import java.util.UUID;

public class FileConverter<T> {
    private static final String SPACE = " ";

    public static <T> String toVoucherString(VoucherResDTO.FILE file) {
        return file.getVoucherId() + SPACE + file.getAmount() + SPACE + file.getVoucherPolicy();
    }

    public static Voucher toVoucher(String file) {
        StringTokenizer tokens = new StringTokenizer(file, SPACE);

        return Voucher.builder()
                .voucherId(UUID.fromString(tokens.nextToken()))
                .amount(Integer.parseInt(tokens.nextToken()))
                .voucherPolicy(VoucherPolicy.valueOf(tokens.nextToken()))
                .build();
    }
}
