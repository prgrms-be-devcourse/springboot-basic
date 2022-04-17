package kdt.vouchermanagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherMemoryRepositoryTest {

    VoucherMemoryRepository voucherMemoryRepository = new VoucherMemoryRepository();

    @Test
    @DisplayName("바우처 저장_성공")
    void saveVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        String voucherTypeNum = "1";
        String discountValue = "10000";
        Voucher voucher = new FixedAmountVoucher(voucherId, VoucherType.from(voucherTypeNum), discountValue);

        //when
        Voucher savedVoucher = voucherMemoryRepository.save(voucher);

        //then
        assertThat(savedVoucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(savedVoucher.getVoucherType()).isEqualTo(VoucherType.from(voucherTypeNum));
    }
}
