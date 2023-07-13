package kr.co.programmers.springbootbasic.voucher.dto;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

public record VoucherCreateRequest(VoucherType type, long amount) {
}
