package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherProvider.getVoucher;

@Component
public class FileParser {

    private static final String FAIL_PARSE = "숫자 입력값 변환 오류 발생";
    private static final String VOUCHER_INFO_SPLIT_STANDARD = "/";
    private static final String AMOUNT_BEFORE_REPLACE_CHAR = ",";
    private static final String AMOUNT_AFTER_REPLACE_CHAR = "";
    private static final int VOUCHER_ID_INDEX = 0;
    private static final int VOUCHER_CLASS_NAME_INDEX = 1;
    private static final int VOUCHER_AMOUNT_INDEX = 2;
    private static final Logger logger = LoggerFactory.getLogger(FileParser.class);

    public String getVoucherInfo(Voucher voucher) {
        String voucherInfo = MessageFormat.format("{0}/{1}/{2}\n",
                voucher.getVoucherId().toString(), voucher.getClass().getSimpleName(), voucher.getAmount());
        logger.info("voucher 객체를 string으로 변환 -> {}", voucherInfo);
        return voucherInfo;
    }

    public Voucher createVoucher(String readVoucherLine) {
        List<String> info = getSplitVoucherInfo(readVoucherLine);
        UUID voucherId = UUID.fromString(
                info.get(VOUCHER_ID_INDEX));
        String className = info.get(VOUCHER_CLASS_NAME_INDEX);
        double amount = parseAmount(info.get(VOUCHER_AMOUNT_INDEX));

        VoucherType voucherType = VoucherType.findVoucherTypeByClassName(className);

        return getVoucher(voucherType, voucherId, amount);
    }

    private List<String> getSplitVoucherInfo(String readVoucher) {
        return Arrays.stream(
                        readVoucher.split(VOUCHER_INFO_SPLIT_STANDARD))
                .toList();
    }

    private double parseAmount(String amount) {
        try {
            return Double.parseDouble(
                    amount.replace(AMOUNT_BEFORE_REPLACE_CHAR, AMOUNT_AFTER_REPLACE_CHAR));
        } catch (NumberFormatException e) {
            logger.error("숫자가 아닌 값이 입력되어 변환에 실패하였습니다.");
            throw new AmountException(FAIL_PARSE);
        }
    }
}
