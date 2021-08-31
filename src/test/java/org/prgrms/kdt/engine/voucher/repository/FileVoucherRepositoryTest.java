package org.prgrms.kdt.engine.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileVoucherRepositoryTest {
    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository();
    Voucher newVoucher;

    @Test
    @DisplayName("바우처를 추가할 수 있다")
    @Order(1)
    void testInsert() {
        newVoucher = VoucherType.FIXED.createVoucher(10);
        fileVoucherRepository.insert(newVoucher);

        var maybeNewVoucher = fileVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(maybeNewVoucher.isEmpty(), is(false));
        assertThat(maybeNewVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("ID로 바우처를 조회할 수 있다")
    @Order(2)
    void testFindById() {
        var maybeNewVoucher = fileVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(maybeNewVoucher.isEmpty(), is(false));
        assertThat(maybeNewVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("모든 바우처를 반환할 수 있다")
    @Order(3)
    void testGetAll() {
        var vouchers = fileVoucherRepository.getAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.get().size(), is(1));
    }
}
