package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.junit.jupiter.api.*;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoucherMemoryRepositoryTest {

    VoucherMemoryRepository voucherMemoryRepository = new VoucherMemoryRepository();

    @Test
    @Order(1)
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

    @Test
    @Order(2)
    @DisplayName("VoucherType과 DiscountValue로 바우처 조회_성공")
    void findVoucherByVoucherTypeAndDiscountValue() {
        //given
        UUID voucherId = UUID.randomUUID();
        String voucherTypeNum = "1";
        String discountValue = "10000";
        voucherMemoryRepository.save(new FixedAmountVoucher(voucherId, VoucherType.from(voucherTypeNum), discountValue));

        //when
        Optional<Voucher> foundVoucher = voucherMemoryRepository.findByVoucherTypeAndDiscountValue(VoucherType.from(voucherTypeNum), discountValue);

        //then
        assertThat(foundVoucher.isPresent()).isEqualTo(true);
        assertThat(foundVoucher.get().getVoucherId()).isEqualTo(voucherId);
        assertThat(foundVoucher.get().getVoucherType()).isEqualTo(VoucherType.from(voucherTypeNum));
    }
}
