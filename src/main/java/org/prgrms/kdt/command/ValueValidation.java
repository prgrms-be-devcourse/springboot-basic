package org.prgrms.kdt.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueValidation {

    private static final Logger logger = LoggerFactory.getLogger(ValueValidation.class);

    public static boolean voucherTypeValidation(final String voucherType) {
        switch (voucherType) {
            case "FixedAmountVoucher", "PercentDiscountVoucher" -> {
                return true;
            }
            default -> {
                Output.wrongVoucherTypeMessage(voucherType);
                return false;
            }
        }
    }

    public static boolean numberValidation(final String voucherType, final String value) {
        try {
            Long.parseLong(value);
        } catch (final NumberFormatException e) {
            logger.error("{} -> 숫자를 입력하세요", e.getMessage());
            return false;
        } catch (final Exception e) {
            logger.error("{}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean rangeValidation(final String voucherType, final String value) {
        final long parsingValue = Long.parseLong(value);
        switch (voucherType) {
            case "FixedAmountVoucher":
                if (parsingValue <= 1000000 && parsingValue > 0)
                    return false;
                else {
                    System.out.println("할인 가격은 0원 초과, 1,000,000원 이하로 설정해주십시오.");
                    return true;
                }
            case "PercentDiscountVoucher":
                if (parsingValue <= 100 && parsingValue > 0)
                    return false;
                else {
                    System.out.println("할인율은 0% 초과, 100% 이하로 설정해주십시오.");
                    return true;
                }
            default:
                System.out.println("잘못된 voucher type 입니다.");
                return true;
        }
    }
}
