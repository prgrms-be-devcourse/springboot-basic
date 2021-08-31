package org.programmers.voucher;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    private static final Logger log = LoggerFactory.getLogger(VoucherServiceTest.class);

    @Test
    void createVoucher() {
        // given
        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        VoucherFactory voucherFactoryMock = mock(VoucherFactory.class);

        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        log.info("voucher id -> {}", fixedAmountVoucher.getVoucherId());
        when(voucherFactoryMock.getVoucherType(VoucherType.FIXED, any(), 100L)).thenReturn(fixedAmountVoucher);

        VoucherService sut = new VoucherService(voucherRepositoryMock, voucherFactoryMock);

        // when
        Voucher voucher = sut.createVoucher(VoucherType.FIXED, 100L);

        // then
        verify(voucherFactoryMock).getVoucherType(VoucherType.FIXED, any(), 100L);
        verify(voucherRepositoryMock).insert(voucher);
    }

    @Test
    void getVoucher() {
        // given
        // when
        // then
    }

    @Test
    void getAllVouchers() {
        // given
        // when
        // then
    }

//    static UUID toUUID(byte[] bytes) {
//        var byteBuffer = ByteBuffer.wrap(bytes);
//        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
//    }
}