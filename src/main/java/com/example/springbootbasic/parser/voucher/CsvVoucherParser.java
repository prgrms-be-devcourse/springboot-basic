package com.example.springbootbasic.parser.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static com.example.springbootbasic.exception.ParserExceptionMessage.CSV_VOUCHER_PARSER_EXCEPTION;
import static com.example.springbootbasic.util.CharacterUnit.ENTER;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

public class CsvVoucherParser {

    private static final Logger logger = LoggerFactory.getLogger(CsvVoucherParser.class);
    private static final int VOUCHER_ID_INDEX = 0;
    private static final int VOUCHER_TYPE_INDEX = 1;
    private static final int VOUCHER_DISCOUNT_VALUE_INDEX = 2;

    public Voucher toVoucherFrom(String voucherCsv) {
        String[] voucherPiece = voucherCsv.split(SPACE.unit());
        String inputVoucherId = voucherPiece[VOUCHER_ID_INDEX].trim();
        String inputVoucherType = voucherPiece[VOUCHER_TYPE_INDEX].trim();
        String inputDiscountValue = voucherPiece[VOUCHER_DISCOUNT_VALUE_INDEX].trim();
        validateDigit(inputVoucherId);
        validateString(inputVoucherType);
        validateDigit(inputDiscountValue);
        long voucherId = Long.parseLong(inputVoucherId);
        VoucherType voucherType = VoucherType.of(inputVoucherType);
        long discountValue = Long.parseLong(inputDiscountValue);
        return VoucherFactory.of(voucherId, discountValue, voucherType,
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(30));
    }

    public String toCsvFrom(Voucher voucher) {
        return String.format("%s %s %s %s",
                voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue(), ENTER.unit());
    }

    private void validateDigit(String inputVoucher) {
        if (!inputVoucher.chars().allMatch(Character::isDigit)) {
            logger.error("Fail - {}", String.format(CSV_VOUCHER_PARSER_EXCEPTION.getMessage(), inputVoucher));
            throw new IllegalArgumentException(String.format(CSV_VOUCHER_PARSER_EXCEPTION.getMessage(), inputVoucher));
        }
    }

    private void validateString(String inputVoucher) {
        if (!inputVoucher.chars().allMatch(codePoint -> Character.isAlphabetic(codePoint) || codePoint == '_')) {
            logger.error("Fail - {}", String.format(CSV_VOUCHER_PARSER_EXCEPTION.getMessage(), inputVoucher));
            throw new IllegalArgumentException(String.format(CSV_VOUCHER_PARSER_EXCEPTION.getMessage(), inputVoucher));
        }
    }
}
