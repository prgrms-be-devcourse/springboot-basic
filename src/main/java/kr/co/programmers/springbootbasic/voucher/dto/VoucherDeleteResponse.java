package kr.co.programmers.springbootbasic.voucher.dto;

import java.util.UUID;

public record VoucherDeleteResponse(UUID voucherId, boolean isSuccess) {
}
