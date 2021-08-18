package org.prgrms.kdt.voucher;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 9:53 오전
 */
class VoucherServiceTest {

    private VoucherService voucherService;

    @BeforeEach
    void beforeEach() {
        voucherService = new VoucherService(new InMemoryVoucherRepository());
    }

    @Test
    @DisplayName("FixedAmountVoucher 생성 테스트")
    void createFixedVoucher() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, 100L);

        voucherService.createVoucher(voucher);

        Voucher saveVoucher = voucherService.getVoucher(voucherId);
        assertNotNull(saveVoucher);

    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 테스트")
    void createPercentVoucher() {
        UUID voucherId = UUID.randomUUID();
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, 30L);

        voucherService.createVoucher(voucher);

        Voucher saveVoucher = voucherService.getVoucher(voucherId);
        assertNotNull(saveVoucher);

    }

    @Test
    @DisplayName("FixedAmountVoucher 전체 조회 테스트")
    void getAllFixedAmountVoucher() {
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        FixedAmountVoucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 40L);
        FixedAmountVoucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 900L);
        voucherService.createVoucher(voucher1);
        voucherService.createVoucher(voucher2);
        voucherService.createVoucher(voucher3);

        Map<UUID, Voucher> vouchers = voucherService.getAllVoucher();

        vouchers.values().forEach(Assertions::assertNotNull);
    }

    @Test
    @DisplayName("PercentDiscountVoucher 전체 조회 테스트")
    void getAllPercentDiscountVoucher() {
        PercentDiscountVoucher voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        PercentDiscountVoucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 40L);
        PercentDiscountVoucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 80L);
        voucherService.createVoucher(voucher1);
        voucherService.createVoucher(voucher2);
        voucherService.createVoucher(voucher3);

        Map<UUID, Voucher> vouchers = voucherService.getAllVoucher();

        vouchers.values().forEach(Assertions::assertNotNull);
    }

    @Test
    @DisplayName("FixAmountVoucher toString 테스트")
    void fixAmountVoucherToString() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, 10L);

        assertEquals("FixedAmountVoucher {voucherId = " + voucherId + ", amount = 10}", voucher.toString());

    }

    @Test
    @DisplayName("PercentDiscountVoucher toString 테스트")
    void PercentDiscountVoucherToString() {
        UUID voucherId = UUID.randomUUID();
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, 10L);

        assertEquals("PercentDiscountVoucher {voucherId = " + voucherId + ", percent = 10}", voucher.toString());

    }

    @Test
    @DisplayName("조회한 바우처 목록에 바우처를 추가하면 예외 발생")
    void invalidGetAllVouchers() {
        Map<UUID, Voucher> allVoucher = voucherService.getAllVoucher();

        UUID voucherId = UUID.randomUUID();
        assertThrows(UnsupportedOperationException.class,
                () -> allVoucher.put(voucherId, new FixedAmountVoucher(voucherId, 10L)));
    }
}