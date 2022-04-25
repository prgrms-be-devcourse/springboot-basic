package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.repository.VoucherFileRepository;
import kdt.vouchermanagement.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherFileRepositoryTest {

    VoucherRepository voucherRepository;
    Voucher firstVoucher = new FixedAmountVoucher(VoucherType.FIXED_AMOUNT, 100);
    String wrongFilePath = "blahblah";
    String filePath = "src/main/resources/voucher";

    @Test
    @Order(1)
    @DisplayName("파일에 바우처 저장할 때 IOException이 발생한다면 IllegalStateException을 발생시킨다_실패")
    void IOExceptionWhenSave() {
        //given
        voucherRepository = new VoucherFileRepository(wrongFilePath);

        //when, then
        assertThrows(IllegalStateException.class, () -> voucherRepository.save(firstVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("파일에 바우처 저장할 때 전달받은 Voucher가 NULL이라면 IllegalArgumentException이 발생한다._실패")
    void nullArgumentWhenSave() {
        //given
        voucherRepository = new VoucherFileRepository(filePath);
        Voucher voucher = null;

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherRepository.save(voucher));
    }

    @Test
    @Order(3)
    @DisplayName("바우처 저장할 때 전달받은 Voucher가 정상적이라면 저장한 Voucher를 반환_성공")
    void returnSavedVoucherWhenSave() {
        //given, when
        Voucher savedVoucher = voucherRepository.save(firstVoucher);

        //then
        assertThat(firstVoucher.getVoucherType()).isEqualTo(savedVoucher.getVoucherType());
        assertThat(firstVoucher.getDiscountValue()).isEqualTo(savedVoucher.getDiscountValue());
    }

    @Test
    @Order(4)
    @DisplayName("저장소에 저장된 모든 바우처들을 조회 후 바우처 리스트 반환_성공")
    void findAllVouchers() {
        //when
        List<Voucher> foundVouchers = voucherRepository.findAll();

        //then
        assertThat(foundVouchers.size()).isEqualTo(1);
        assertThat(foundVouchers.get(0).getVoucherType()).isEqualTo(firstVoucher.getVoucherType());
        assertThat(foundVouchers.get(0).getDiscountValue()).isEqualTo(firstVoucher.getDiscountValue());
    }
}
