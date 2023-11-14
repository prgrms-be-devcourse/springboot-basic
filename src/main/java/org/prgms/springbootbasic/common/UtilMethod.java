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
    } // Dto -> voucher. 1. dto에서 정적 팩토리 메서드로. 2. 매퍼 클래스를 만드는거. 클래스 역할과 책임에 따라. 의존관계에 따른 고민.
}
