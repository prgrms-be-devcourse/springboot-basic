package org.prgrms.kdt.voucher;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
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
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, 30);

        voucherService.createVoucher(voucher);

        Voucher saveVoucher = voucherService.getVoucher(voucherId);
        assertNotNull(saveVoucher);

    }
}