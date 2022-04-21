package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherFactory;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoucherFactoryTest {

    @Test
    @Order(1)
    @DisplayName("VoucherType에 따른 VoucherFactory 반환값_성공")
    void fromVoucherFactory() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;

        //then
        Optional<VoucherFactory> voucherFactory = VoucherFactory.from(type);

        //when
        assertThat(voucherFactory.isPresent()).isTrue();
    }

    @Test
    @Order(2)
    @DisplayName("VoucherType에 따른 Voucher 도메인 생성_성공")
    void createVoucher() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        int discountValue = 100;
        Voucher voucher = new FixedAmountVoucher(type, discountValue);
        Optional<VoucherFactory> voucherFactory = VoucherFactory.from(type);

        //then
        Voucher createdVoucher = voucherFactory.get().create(discountValue);

        //when
        assertThat(createdVoucher).usingRecursiveComparison().isEqualTo(voucher);
    }
}
