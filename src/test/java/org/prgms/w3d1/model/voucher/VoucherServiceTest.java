package org.prgms.w3d1.model.voucher;

import org.junit.jupiter.api.Test;
import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class VoucherServiceTest {

    private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
    private final VoucherService voucherService = new VoucherService(voucherRepository);

    /*
        테스트 : 바우처를 가져올수 있는가
        Given : 바우처를 생성하고
        When : findById가 호출되면 voucher가 리턴될 때
        then : 내부 메서드의 실행을 검사한다.
     */
    @Test
    void getVoucher() {
        // Given
        Voucher voucher = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
        // When
        var testVoucher = voucherService.getVoucher(voucher.getVoucherId());

        // then
        assertThat(testVoucher, samePropertyValuesAs(voucher));
        verify(voucherRepository).findById(voucher.getVoucherId());
    }

    /*
        테스트 : saveVoucher() 가 호출될 때 내부 동작 순서 검증
        Given : 바우처를 생성하고
        When : saveVoucher 메서드가 호출되면
        then : 내부 메서드의 실행을 검사한다.
    */
    @Test
    void saveVoucher() {
        // Given
        Voucher voucher = FixedAmountVoucher.of(UUID.randomUUID(), 100L);

        // when
        voucherService.saveVoucher(voucher);

        // then
        verify(voucherRepository).save(voucher);
    }

    /*
        테스트 : findAll() 이 호출될 때 내부 동작 순서 검증
        Given :
        When : saveVoucher 메서드가 호출되면
        then : 내부 메서드의 실행을 검사한다.
    */
    @Test
    void findAll() {
        // Given

        // When
        var voucherList = voucherService.findAll();

        // then
        verify(voucherRepository).findAll();
    }
}