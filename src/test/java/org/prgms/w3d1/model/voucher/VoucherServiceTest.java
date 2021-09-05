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

    /*
        Given : Customer와 voucherWallet을 만들고
        when : customer에게 voucherWallet을 할당할때
        then : 할당이 되었는지 get 메서드를 통해서 확인한다.
     */

    @Test
    void testSetVoucherWallet() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        VoucherWallet voucherWallet = new VoucherWallet(List.of(FixedAmountVoucher.of(UUID.randomUUID(), 100L)));
        voucherService.serVoucherWallet(customer, voucherWallet);

        assertThat(customer.getVoucherWallet().equals(voucherWallet), is(true));
    }

    /*
        given : 특정 고객으로 바우처 지갑을 만들고
        when : 해당 고객 id로 바우처 지갑을 찾을 때,
        then : repository의 findVoucherWallet이 실행되야한다.
     */
    @Test
    void testFindVoucherWallet() {
        var customerId = UUID.randomUUID();
        var voucherWallet = new VoucherWallet(List.of(new FixedAmountVoucher(customerId, 100L, customerId)));
        // When
        when(voucherService.findVoucherWallet(customerId)).thenReturn(voucherWallet);
        var testVoucherWallet = voucherService.findVoucherWallet(customerId);

        // then
        assertThat(testVoucherWallet.equals(voucherWallet), is(true));
        verify(voucherRepository).findVoucherWallet(customerId);
    }

}