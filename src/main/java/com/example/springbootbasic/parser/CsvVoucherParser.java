package com.example.springbootbasic.parser;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherEnum;

import static com.example.springbootbasic.util.CharacterUnit.ENTER;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

public class CsvVoucherParser {
    private static final int VOUCHER_ID_INDEX = 0;
    private static final int VOUCHER_TYPE_INDEX = 1;
    private static final int VOUCHER_DISCOUNT_VALUE_INDEX = 2;

    public Voucher parseToVoucherFrom(String voucherCsv) {
        String[] voucherPiece = voucherCsv.split(SPACE.getUnit());
        Long voucherId = Long.parseLong(voucherPiece[VOUCHER_ID_INDEX]);
        String voucherType = voucherPiece[VOUCHER_TYPE_INDEX];
        Long voucherValue = Long.parseLong(voucherPiece[VOUCHER_DISCOUNT_VALUE_INDEX]);
        return VoucherEnum.generateVoucher(
                voucherId,
                voucherValue,
                VoucherEnum.findVoucherBy(voucherType)
                        .orElseThrow(() -> new IllegalArgumentException()));
    }

    public String parseToCsvFrom(Long voucherId, Voucher voucher) {
        return voucherId +
                SPACE.getUnit() +
                voucher.getVoucherEnum().getVoucherType() +
                SPACE.getUnit() +
                voucher.getDiscountValue() +
                ENTER.getUnit();
    }
}
