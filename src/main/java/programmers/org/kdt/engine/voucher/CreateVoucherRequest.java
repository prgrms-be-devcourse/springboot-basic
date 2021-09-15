package programmers.org.kdt.engine.voucher;

import programmers.org.kdt.engine.voucher.type.VoucherStatus;

public record CreateVoucherRequest (int status, long input_value) {
}

