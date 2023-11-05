package org.prgms.springbootbasic.common;

import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherResponseDto;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UtilMethod {
    public static UUID bytesToUUID(byte[] bytes) {
        ByteBuffer wrappedByte = ByteBuffer.wrap(bytes);

        return new UUID(wrappedByte.getLong(), wrappedByte.getLong());
    }

    public static VoucherResponseDto convertVoucherToVoucherResponseDto(Voucher voucher) {
        return new VoucherResponseDto(voucher.getVoucherId(),
                voucher.getDiscountDegree(),
                voucher.getVoucherPolicy().getClass().getSimpleName(),
                voucher.getCreatedAt());
    }
}
