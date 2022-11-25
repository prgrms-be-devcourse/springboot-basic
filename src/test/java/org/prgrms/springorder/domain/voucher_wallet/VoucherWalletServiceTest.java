package org.prgrms.springorder.domain.voucher_wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockedStatic;
import org.prgrms.springorder.domain.voucher_wallet.model.Wallet;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher_wallet.model.VoucherWallet;
import org.prgrms.springorder.domain.voucher_wallet.repository.VoucherWalletRepository;
import org.prgrms.springorder.domain.voucher_wallet.service.VoucherWalletService;
import org.prgrms.springorder.global.exception.EntityNotFoundException;

@TestInstance(Lifecycle.PER_CLASS)
class VoucherWalletServiceTest {

    private VoucherWalletService voucherWalletService;
    private VoucherWalletRepository voucherWalletRepository;

    @BeforeAll
    public void beforeAll() {
        voucherWalletRepository = mock(VoucherWalletRepository.class);
        voucherWalletService = new VoucherWalletService(voucherWalletRepository);
    }

    @AfterAll
    public void afterAll() {

    }

    @DisplayName("유저에게 바우처를 할당한다.")
    @Test
    void allocateVoucherSuccessTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        UUID walletId = UUID.randomUUID();

        try (MockedStatic<VoucherWallet> voucherWalletMockedStatic = mockStatic(
            VoucherWallet.class)) {

            VoucherWallet voucherWallet = VoucherWallet.create(walletId, customerId,
                voucherId);

            given(VoucherWallet.create(walletId, customerId, voucherId))
                .willReturn(voucherWallet);

            given(voucherWalletRepository.insert(voucherWallet))
                .willReturn(voucherWallet);

            //when
            voucherWalletService.allocateVoucher(customerId, voucherId);
            //then

            verify(voucherWalletRepository).insert(voucherWallet);
            voucherWalletMockedStatic.verify(
                () -> VoucherWallet.create(walletId, customerId, voucherId));
        }
    }

    @DisplayName("고객이 보유한 바우처를 제거한다. ")
    @Test
    void deleteVoucherByCustomerIdSuccessTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        given(voucherWalletRepository.existsByCustomerIdAndVoucherId(customerId, voucherId))
            .willReturn(true);

        willDoNothing().given(voucherWalletRepository)
            .deleteByCustomerIdAndVoucherID(customerId, voucherId);

        //when
        voucherWalletService.deleteVoucherByCustomerId(voucherId, customerId);

        //then
        verify(voucherWalletRepository)
            .existsByCustomerIdAndVoucherId(customerId, voucherId);
        verify(voucherWalletRepository)
            .deleteByCustomerIdAndVoucherID(customerId, voucherId);
    }

    @DisplayName("고객이 보유하고 있지 않은 바우처를 제거하려고 하면 예외가 발생한다. ")
    @Test
    void deleteVoucherByCustomerIdFailTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        given(voucherWalletRepository.existsByCustomerIdAndVoucherId(customerId, voucherId))
            .willReturn(false);

        //when
        assertThrows(EntityNotFoundException.class, () ->
            voucherWalletService.deleteVoucherByCustomerId(voucherId, customerId));

        //then
        verify(voucherWalletRepository)
            .existsByCustomerIdAndVoucherId(customerId, voucherId);
    }

    @DisplayName("고객이 보유한 모든 바우처인 지갑을 반환한다.")
    @Test
    void findAllVouchersSuccessTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        Customer customer = new Customer(customerId, "hi", "test@email.com");

        Voucher voucher = new FixedAmountVoucher(voucherId, 100L);

        Wallet wallet = new Wallet(customer, List.of(voucher));

        given(voucherWalletRepository.findAllWithCustomerAndVoucher(customerId))
            .willReturn(Optional.of(wallet));
        //when
        Wallet findWallet = voucherWalletService.findAllVouchers(customerId);
        //then
        verify(voucherWalletRepository).findAllWithCustomerAndVoucher(customerId);
        assertEquals(wallet, findWallet);
    }

    @DisplayName("고객이 보유한 바우처가 없으면 지갑대신 예외를 던진다.")
    @Test
    void findAllVouchersFailTest() {
        //given
        UUID customerId = UUID.randomUUID();

        given(voucherWalletRepository.findAllWithCustomerAndVoucher(customerId))
            .willReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
            () -> voucherWalletService.findAllVouchers(customerId));

        //then
        verify(voucherWalletRepository).findAllWithCustomerAndVoucher(customerId);
    }

    @DisplayName("커스토머와 고객을 함께 조회해온다.")
    @Test
    void findCustomerWithVoucherSuccessTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        CustomerWithVoucher customerWithVoucher = new CustomerWithVoucher(voucherId, 100L,
            LocalDateTime.now(), VoucherType.PERCENT, customerId, "name", "email@gamil.com",
            CustomerStatus.NORMAL);

        given(voucherWalletRepository.findByVoucherIdWithCustomer(voucherId))
            .willReturn(Optional.of(customerWithVoucher));

        //when
        CustomerWithVoucher findCustomerWithVoucher = voucherWalletService.findCustomerWithVoucherByVoucherId(
            voucherId);

        //then
        verify(voucherWalletRepository).findByVoucherIdWithCustomer(voucherId);
        assertEquals(customerWithVoucher, findCustomerWithVoucher);
    }

    @DisplayName("지갑에 커스토머와 고객이 없으면 예외를 던진다")
    @Test
    void findCustomerWithVoucherFailTest() {
        //given
        UUID voucherId = UUID.randomUUID();

        given(voucherWalletRepository.findByVoucherIdWithCustomer(voucherId))
            .willReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
            () -> voucherWalletService.findCustomerWithVoucherByVoucherId(
                voucherId));

        //then
        verify(voucherWalletRepository).findByVoucherIdWithCustomer(voucherId);

    }

}