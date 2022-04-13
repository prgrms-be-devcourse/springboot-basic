package org.prgrms.vouchermanager.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.voucher.domain.AbstractVoucher;
import org.prgrms.vouchermanager.voucher.domain.Voucher;
import org.prgrms.vouchermanager.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FileVoucherRepositoryTest {

    class MockVoucher extends AbstractVoucher {
        public MockVoucher(VoucherType type) {
            super(type);
        }

        @Override
        public Long discount(long beforeDiscount) {
            return null;
        }
    }

    @Test
    @DisplayName("파일로 voucher를 저장한다.")
    void insert() {
        //given
        VoucherRepository voucherRepository = new FileVoucherRepository("C:\\save");
        Voucher voucher = new MockVoucher(VoucherType.FIXED);

        //when
        Voucher inserted = voucherRepository.insert(voucher);

        //then
        assertEquals(voucher, inserted);
    }

    @Test
    @DisplayName("파일에서 voucherId로 조회한다.")
    void findById() {
        //given
        VoucherRepository voucherRepository = new FileVoucherRepository();
        Voucher voucher = new MockVoucher(VoucherType.FIXED);
        //when
        voucherRepository.insert(voucher);

        //then
        assertEquals(voucher, voucherRepository.findById(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("파일에서 저장된 모든 voucher의 list를 반환한다.")
    void getAll() {

    }

}