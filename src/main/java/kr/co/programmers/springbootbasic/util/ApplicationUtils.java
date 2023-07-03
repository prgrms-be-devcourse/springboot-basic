package kr.co.programmers.springbootbasic.util;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerDto;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class ApplicationUtils {
    private static final String NO_VALID_NUMBER_INPUT_LOG = """
            사용자가 숫자가 아닌 {}를 입력했습니다.
                        
            """;
    private static final String FIXED_VOUCHER_FORMAT = """
            종류 : {0}
            아이디 : {1}
            할인 가격 : {2}원
            생성시기 : {3}
                 
            """;
    private static final String PERCENT_VOUCHER_FORMAT = """
            종류 : {0}
            아이디 : {1}
            할인 가격 : {2}%
            생성시기 : {3}
                        
            """;
    private static final String CSV_USER_FORMAT = """
            고객 아이디 : {0}
            고객 이름 : {1}
            고객 상태 : {2}
                        
            """;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);

    private ApplicationUtils() {
    }

    public static int parseStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            logger.warn(NO_VALID_NUMBER_INPUT_LOG, input);
            throw new NumberFormatException("올바른 숫자입력이 아닙니다.\n\n");
        }
    }

    public static long parseStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            logger.warn(NO_VALID_NUMBER_INPUT_LOG, input);
            throw new NumberFormatException("올바른 숫자입력이 아닙니다.\n\n");
        }
    }

    public static VoucherDto convertToVoucherResponseDto(Voucher voucher) {
        VoucherType type = voucher.getType();
        UUID voucherId = voucher.getId();
        long amount = voucher.getAmount();
        LocalDateTime createdAt = voucher.getCreatedAt();

        return new VoucherDto(type, voucherId, amount, createdAt);
    }

    public static CustomerDto convertToCustomerResponseDto(Customer customer) {
        UUID id = customer.getId();
        String name = customer.getName();
        CustomerStatus status = customer.getStatus();
        UUID walletId = customer.getWalletId();

        return new CustomerDto(id, name, status, walletId);
    }

    public static String formatVoucherResponseDto(VoucherDto dto) {
        if (dto.getType() == VoucherType.FIXED_AMOUNT) {
            return MessageFormat.format(FIXED_VOUCHER_FORMAT,
                    dto.getType(),
                    dto.getVoucherId(),
                    dto.getAmount(),
                    dto.getCreatedAt());
        }

        return MessageFormat.format(PERCENT_VOUCHER_FORMAT,
                dto.getType(),
                dto.getVoucherId(),
                dto.getAmount(),
                dto.getCreatedAt());
    }

    public static String formatCustomerResponseDto(CustomerDto dto) {
        return MessageFormat.format(CSV_USER_FORMAT,
                dto.getId(),
                dto.getName(),
                dto.getStatus()
        );
    }

    public static UUID createUUID() {
        return UUID.randomUUID();
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
