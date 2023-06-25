package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;

public interface Voucher {
    long discount(long price);
    VoucherResponseDto getVoucherInfo();
}
