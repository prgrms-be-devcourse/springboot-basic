package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public class VoucherDto {
    public static record Request(
            VoucherType type,
            int value
    ) {
        public static Voucher toDomain(Request request) {
            return new Voucher(
                    request.type(),
                    request.value()
            );
        }
    }

    public static record Response(
            Long id,
            VoucherType type,
            int value,
            ResponseStatus status
    ) {
        public static Response of(Voucher voucher, ResponseStatus status) {
            return new Response(
                    voucher.getId(),
                    voucher.getType(),
                    voucher.getValue(),
                    status
            );
        }

        public static Response error(ResponseStatus status) {
            return new Response(null, null, 0, status);
        }
    }
}
