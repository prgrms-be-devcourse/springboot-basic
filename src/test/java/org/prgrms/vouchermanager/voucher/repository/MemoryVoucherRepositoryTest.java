package org.prgrms.vouchermanager.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.voucher.domain.AbstractVoucher;
import org.prgrms.vouchermanager.voucher.domain.Voucher;
import org.prgrms.vouchermanager.voucher.domain.VoucherType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class MemoryVoucherRepositoryTest {

    @Test
    @DisplayName("Voucher를 insert하고 똑같은 객체를 반환받는다.")
    void testWithInsert() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        Voucher voucher = mock(Voucher.class);

        //when
        Voucher inserted = voucherRepository.insert(voucher);

        //then
        assertEquals(voucher, inserted);
    }

    @Test
    @DisplayName("voucherId로 Voucher를 찾을 수 있다.")
    void testWithFindById() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        Voucher voucher = mock(Voucher.class);

        //when
        voucherRepository.insert(voucher);
        Voucher findVoucher = voucherRepository.findById(voucher.getVoucherId()).get();

        //then
        assertEquals(voucher, findVoucher);
    }

    @Test
    @DisplayName("이미 존재하는 voucher를 insert 할 수 없다.")
    void testWithDuplicatedInsert() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        Voucher voucher = mock(Voucher.class);
        voucherRepository.insert(voucher);

        //then
        assertThrows(IllegalArgumentException.class, () -> voucherRepository.insert(voucher));
    }

}