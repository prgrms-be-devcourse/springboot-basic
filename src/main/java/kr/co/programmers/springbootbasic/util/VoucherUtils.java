package kr.co.programmers.springbootbasic.util;

import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherUtils {
    private static final String NO_VALID_NUMBER_INPUT_LOG = """
            사용자가 숫자가 아닌 {}를 입력했습니다.
                        
            """;
    private static final String NO_VALID_NUMBER_INPUT = """
            올바른 숫자입력이 아닙니다.
                        
            """;
    private static final String FIXED_VOUCHER_FORMAT = """
            종류 : {0}
            아이디 : {1}
            할인 가격 : {2}원
                        
            """;
    private static final String PERCENT_VOUCHER_FORMAT = """
            종류 : {0}
            아이디 : {1}
            할인 가격 : {2}%
                        
            """;

    private static final Logger logger = LoggerFactory.getLogger(VoucherUtils.class);


    private VoucherUtils() {
    }

    public static int parseStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            logger.warn(NO_VALID_NUMBER_INPUT_LOG, input);
            throw new NumberFormatException(NO_VALID_NUMBER_INPUT);
        }
    }

    public static long parseStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            logger.warn(NO_VALID_NUMBER_INPUT_LOG, input);
            throw new NumberFormatException(NO_VALID_NUMBER_INPUT);
        }
    }

    public static String formatVoucherResponseDto(VoucherResponseDto dto) {
        VoucherType type = dto.getType();
        UUID voucherId = dto.getVoucherId();
        long amount = dto.getAmount();

        if (type == VoucherType.PERCENT_AMOUNT) {
            return MessageFormat.format(PERCENT_VOUCHER_FORMAT, type, voucherId, amount);
        }
        return MessageFormat.format(FIXED_VOUCHER_FORMAT, type, voucherId, amount);
    }
}
