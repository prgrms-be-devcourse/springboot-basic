package kr.co.programmers.springbootbasic.util;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private static final String CUSTOMER_FORMAT = """
            고객 아이디 : {0}
            고객 이름 : {1}
            고객 상태 : {2}
                        
            """;
    private static final String WALLET_SAVE_FORMAT = """
            바우처 아이디 : {0}
            지갑 아이디 : {1}
                        
            """;
    private static final String WALLET_FIND_RESPONSE_FORMAT = """
            지갑 아이디 : {0}
                        
            바우처 정보
            =================================
                        
            {1}
                    
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

    public static VoucherResponse convertToVoucherResponse(Voucher voucher) {
        VoucherType type = voucher.getType();
        UUID voucherId = voucher.getId();
        long amount = voucher.getAmount();
        LocalDateTime createdAt = voucher.getCreatedAt();
        UUID walletId = voucher.getWalletId();

        return new VoucherResponse(type, voucherId, amount, createdAt, walletId);
    }

    public static CustomerResponse convertToCustomerResponse(Customer customer) {
        UUID id = customer.getId();
        String name = customer.getName();
        CustomerStatus status = customer.getStatus();
        UUID walletId = customer.getWalletId();

        return new CustomerResponse(id, name, status, walletId);
    }

    public static String formatVoucherResponseDto(VoucherResponse dto) {
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

    public static String formatCustomerResponseDto(CustomerResponse dto) {
        return MessageFormat.format(CUSTOMER_FORMAT,
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

    public static String formatWalletSaveDto(WalletSaveDto responseDto) {
        String voucherId = responseDto.getVoucherId();
        String walletId = responseDto.getWalletId();

        return MessageFormat.format(WALLET_SAVE_FORMAT,
                voucherId,
                walletId);
    }

    public static String formatWalletFindResponse(WalletResponse walletResponse) {
        UUID walletId = walletResponse.getWalletId();
        List<VoucherResponse> voucherResponses = walletResponse.getVoucherResponses();
        String messages = voucherResponses.stream().map(ApplicationUtils::formatVoucherResponseDto).collect(Collectors.joining());

        return MessageFormat.format(WALLET_FIND_RESPONSE_FORMAT, walletId, messages);
    }
}
