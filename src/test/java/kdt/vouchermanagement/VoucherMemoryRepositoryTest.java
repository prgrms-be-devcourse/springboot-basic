package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.repository.VoucherMemoryRepository;
import kdt.vouchermanagement.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoucherMemoryRepositoryTest {

    private VoucherRepository voucherRepository = new VoucherMemoryRepository();

    @Test
    @DisplayName("바우처 저장할 때 전달받은 Voucher가 NULL이라면 IllegalArgumentException이 발생한다._실패")
    void nullArgumentWhenSave() {
        //given
        Voucher voucher = null;

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherRepository.save(voucher));
    }

    @Test
    @DisplayName("바우처 저장할 때 전달받은 Voucher가 정상적이라면 저장한 Voucher를 반환_실패")
    void returnSavedVoucherWhenSave() {
        //given
        Voucher voucher = new FixedAmountVoucher(VoucherType.FIXED_AMOUNT, 100);

        //when
        Voucher savedVoucher = voucherRepository.save(voucher);

        //then
        assertThat(voucher.getVoucherType()).isEqualTo(savedVoucher.getVoucherType());
        assertThat(voucher.getDiscountValue()).isEqualTo(savedVoucher.getDiscountValue());
    }
}
