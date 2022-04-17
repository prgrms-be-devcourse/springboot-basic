package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.repository.VoucherRepository;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    VoucherService voucherService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherServiceImpl(voucherRepository);
    }

    @Test
    @DisplayName("생성할 VoucherType과 DiscountValue가 주어질 때 유효하지 않은 VoucherType이면_실패")
    void invalidVoucherType() {
        //given
        String voucherTypeNum = "111";
        String discountValue = "10";

        //when, then
        assertThrows(InvalidVoucherTypeException.class, () -> voucherService.createVoucher(voucherTypeNum, discountValue));
    }

    @Test
    @DisplayName("생성할 VoucherType과 DiscountValue가 주어질 때 유효한 VoucherType이면 _성공")
    void validVoucherType() {
        //given
        String voucherTypeNum = "1";
        String discountValue = "10";

        //when, then
        assertDoesNotThrow(() -> voucherService.createVoucher(voucherTypeNum, discountValue));
    }

    @Test
    @DisplayName("FixedAmountVoucher 타입의 할인값 입력시 문자열을 입력하면_실패")
    void stringDiscountValueWhenFixedAmountVoucher() {
        //given
        String voucherTypeNum = "1";
        String discountValue = "blahblah";

        //when, then
        assertThrows(InvalidDiscountValueException.class, () -> voucherService.createVoucher(voucherTypeNum, discountValue));
    }

    @Test
    @DisplayName("FixedAmountVoucher 타입의 할인값 입력시 음수를 입력하면_실패")
    void negativeDiscountValueWhenFixedAmountVoucher() {
        //given
        String voucherTypeNum = "1";
        String discountValue = "-1";

        //when, then
        assertThrows(InvalidDiscountValueException.class, () -> voucherService.createVoucher(voucherTypeNum, discountValue));
    }

    @Test
    @DisplayName("FixedAmountVoucher 타입의 할인값 입력시 0를 입력하면_실패")
    void zeroDiscountValueWhenFixedAmountVoucher() {
        //given
        String voucherTypeNum = "1";
        String discountValue = "0";

        //when, then
        assertThrows(InvalidDiscountValueException.class, () -> voucherService.createVoucher(voucherTypeNum, discountValue));
    }

    @Test
    @DisplayName("생성할 VoucherType과 DiscountValue가 주어질 때 해당 값을 가진 바우처가 이미 존재하면_실패")
    void isDuplicateVoucher() {
        //given
        String voucherTypeNum = "1";
        String discountValue = "10000";
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), VoucherType.from(voucherTypeNum), discountValue);

        doReturn(Optional.of(voucher)).when(voucherRepository).findByVoucherTypeAndDiscountValue(VoucherType.from(voucherTypeNum), discountValue);

        //when, then
        assertThrows(DuplicateVoucherException.class, () -> voucherService.createVoucher(voucherTypeNum, discountValue));
    }

    @Test
    @DisplayName("FixedAmountVoucher 생성_성공")
    void createFixedAmountVoucher() {
        //given
        String voucherTypeNum = "1";
        String discountValue = "10000";
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), VoucherType.from(voucherTypeNum), discountValue);

        doReturn(voucher).when(voucherRepository).save(any());
        doReturn(Optional.empty()).when(voucherRepository).findByVoucherTypeAndDiscountValue(VoucherType.from(voucherTypeNum), discountValue);

        //when
        Voucher createdVoucher = voucherService.createVoucher(voucherTypeNum, discountValue);

        //then
        assertThat(createdVoucher.getVoucherId()).isNotNull();
        assertThat(createdVoucher.getVoucherType()).isEqualTo(VoucherType.from(voucherTypeNum));
        verify(voucherRepository, times(1)).save(any());
    }
}
