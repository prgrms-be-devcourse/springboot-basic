package kr.co.programmers.springbootbasic.voucher.dto;

import java.text.MessageFormat;
import java.util.UUID;

public record VoucherDeleteResponse(UUID voucherId, boolean isSuccess) {
    public String formatVoucherDeleteResponse() {
        return MessageFormat.format("""
                바우처 아이디
                {0}
                를 삭제했습니다.
                                
                """, voucherId);
    }
}
