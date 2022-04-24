package com.waterfogsw.voucher.voucher.dto;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

public class VoucherDto {
    public static record Request(
            VoucherType type,
            int value
    ) {
        public static Voucher toDomain(Request request) {
            return Voucher.of(request.type(), request.value());
        }
    }

    public static record Response(
            Long id,
            int value,
            ResponseStatus status
    ) {
        public static Response of(Voucher voucher, ResponseStatus status) {
            return new Response(
                    voucher.getId(),
                    voucher.getValue(),
                    status
            );
        }

        public static Response error(ResponseStatus status) {
            return new Response(null, 0, status);
        }
    }
}
