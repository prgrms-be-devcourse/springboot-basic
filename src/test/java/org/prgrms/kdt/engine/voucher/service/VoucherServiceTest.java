package org.prgrms.kdt.engine.voucher.service;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.engine.customer.repository.CustomerMemoryRepository;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.prgrms.kdt.engine.voucher.repository.MemoryVoucherRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


class VoucherServiceTest {
    // 메모리 레포지토리를 stub로 활용
    MemoryVoucherRepository voucherRepositoryStub = new MemoryVoucherRepository();
    CustomerMemoryRepository customerRepositoryStub = new CustomerMemoryRepository();
    VoucherService voucherService = new VoucherService(voucherRepositoryStub, customerRepositoryStub);

    Voucher fixedVoucher = VoucherType.FIXED.createVoucher(10);
    Voucher percentVoucher = VoucherType.PERCENT.createVoucher(15);

    @BeforeEach
    void clean() {
        voucherRepositoryStub.deleteAll();
        customerRepositoryStub.deleteAll();
        voucherRepositoryStub.insert(fixedVoucher);
        voucherRepositoryStub.insert(percentVoucher);
    }

    @Test
    @DisplayName("바우처를 생성할 수 있다")
    void testCreateVoucher() {
        var newFixedVoucher = voucherService.createVoucher(VoucherType.FIXED, 10);
        var newPercentVoucher = voucherService.createVoucher(VoucherType.PERCENT, 15);

        var maybeNewFixedVoucher = voucherService.getVoucher(newFixedVoucher.getVoucherId());
        var maybeNewPercentVoucher = voucherService.getVoucher(newPercentVoucher.getVoucherId());

        assertThat(newFixedVoucher, samePropertyValuesAs(maybeNewFixedVoucher));
        assertThat(newPercentVoucher, samePropertyValuesAs(maybeNewPercentVoucher));
    }

    @Test
    @DisplayName("바우처를 개별 조회할 수 있다")
    void testGetVoucher() {
        var maybeNewVoucher = voucherService.getVoucher(fixedVoucher.getVoucherId());
        assertThat(maybeNewVoucher, samePropertyValuesAs(fixedVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("유효한 바우처만 조회할 수 있다")
    void testGetInvalidVoucher() {
        assertThrows(RuntimeException.class, () -> voucherService.getVoucher(UUID.randomUUID()));
    }

    @Test
    @Order(4)
    @DisplayName("모든 바우처를 조회할 수 있다")
    void testListVoucher() {
        var vouchers = voucherService.listVoucher();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.get().size(), is(2));
    }
}
