package com.example.springbootbasic.parser;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.springbootbasic.util.CharacterUnit.ENTER;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

public class CsvVoucherParser {

    private static final Logger logger = LoggerFactory.getLogger(CsvVoucherParser.class);
    private static final int VOUCHER_ID_INDEX = 0;
    private static final int VOUCHER_TYPE_INDEX = 1;
    private static final int VOUCHER_DISCOUNT_VALUE_INDEX = 2;

    public Voucher parseToVoucherFrom(String voucherCsv) {
        String[] voucherPiece = voucherCsv.split(SPACE.unit());
        long voucherId = Long.parseLong(voucherPiece[VOUCHER_ID_INDEX].trim());
        VoucherType voucherType = VoucherType.findVoucherType(voucherPiece[VOUCHER_TYPE_INDEX].trim());
        long discountValue = Long.parseLong(voucherPiece[VOUCHER_DISCOUNT_VALUE_INDEX].trim());
        return VoucherFactory.generateVoucher(voucherId, discountValue, voucherType);
    }

    public String parseToCsvFrom(Voucher voucher) {
        return String.format("%s %s %s %s",
                voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue(), ENTER.unit());
    }
}
