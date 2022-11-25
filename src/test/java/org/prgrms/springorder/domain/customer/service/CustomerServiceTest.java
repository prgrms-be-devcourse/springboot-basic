package org.prgrms.springorder.domain.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockedStatic;
import org.prgrms.springorder.domain.voucher_wallet.model.Wallet;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.repository.CustomerRepository;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.prgrms.springorder.domain.voucher_wallet.service.VoucherWalletService;
import org.prgrms.springorder.global.exception.EntityNotFoundException;

@TestInstance(Lifecycle.PER_CLASS)
class CustomerServiceTest {

    private MockedStatic<VoucherFactory> voucherFactoryMockedStatic;

    private CustomerRepository customerRepository = mock(CustomerRepository.class);
    private VoucherWalletService voucherWalletService = mock(VoucherWalletService.class);
    private BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
    private VoucherService voucherService = mock(VoucherService.class);
    private CustomerService customerService;

    @BeforeAll
    public void beforeAll() {
        voucherFactoryMockedStatic = mockStatic(VoucherFactory.class);
        customerService = new CustomerService(customerRepository, voucherWalletService,
            blockCustomerService, voucherService);
    }

    @BeforeEach
    void beforeEach() {
        customerRepository = mock(CustomerRepository.class);
        voucherWalletService = mock(VoucherWalletService.class);
        blockCustomerService = mock(BlockCustomerService.class);
        voucherService = mock(VoucherService.class);
        customerService = new CustomerService(customerRepository, voucherWalletService,
            blockCustomerService, voucherService);
    }

    @AfterAll
    public void afterAll() {
        voucherFactoryMockedStatic.close();
    }

    @DisplayName("findAll 테스트 - 고객이 보유한 바우처 지갑을 리턴한다.")
    @Test
    void findAllVouchersSuccessTest() {
        //given

        UUID customerId = UUID.randomUUID();
        String customerName = "name";
        String customerEmail = "email@naver.com";
        Customer customer = new Customer(customerId, customerName, customerEmail);

        UUID fixedVoucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher fixedAmountVoucher = new FixedAmountVoucher(fixedVoucherId, amount);

        UUID percentVoucherId = UUID.randomUUID();
        Voucher percentAmountVoucher = new PercentDiscountVoucher(percentVoucherId, amount);
        List<Voucher> vouchers = List.of(fixedAmountVoucher, percentAmountVoucher);
        Wallet wallet = new Wallet(customer, vouchers);

        when(voucherWalletService.findAllVouchers(customerId))
            .thenReturn(wallet);

        //when
        Wallet findWallet = customerService.findAllVouchers(customerId);

        //then
        assertEquals(customer, findWallet.getCustomer());
        assertEquals(vouchers, findWallet.getVouchers());

        verify(voucherWalletService).findAllVouchers(customerId);
    }

    @DisplayName("findAll 테스트 - 지갑이 비어있따면 예외를 던진다.")
    @Test
    void findAllVouchersFailThrowsExceptionTest() {
        //given
        UUID customerId = UUID.randomUUID();

        when(voucherWalletService.findAllVouchers(customerId))
            .thenThrow(EntityNotFoundException.class);

        //when
        assertThrows(EntityNotFoundException.class,
            () -> customerService.findAllVouchers(customerId));

        //then
        verify(voucherWalletService).findAllVouchers(customerId);
    }

    @DisplayName("delete 테스트 - 고객이 보유한 바우처를 제거한다")
    @Test
    void deleteVoucherSuccessTest() {
        //given

        UUID customerId = UUID.randomUUID();
        String customerName = "name";
        String customerEmail = "email@naver.com";
        Customer customer = new Customer(customerId, customerName, customerEmail);

        UUID fixedVoucherId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
            .thenReturn(Optional.of(customer));

        doNothing().when(voucherWalletService)
            .deleteVoucherByCustomerId(fixedVoucherId, customerId);

        customerService.deleteVoucher(customerId, fixedVoucherId);
        //when & then

        verify(customerRepository).findById(customerId);

        verify(voucherWalletService).deleteVoucherByCustomerId(fixedVoucherId, customerId);
    }

    @DisplayName("delete 테스트 - 고객이 없다면 예외를 던진다")
    @Test
    void deleteVoucherFailThrowsExceptionTest() {
        //given

        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
            .thenReturn(Optional.empty());
        // when

        assertThrows(EntityNotFoundException.class, () ->
            customerService.deleteVoucher(customerId, voucherId));

        //then
        verify(customerRepository).findById(customerId);
    }

    @DisplayName("할당 테스트 - 특정 고객에게 바우처를 할당한다")
    @Test
    void allocateVoucherSuccessTest() {
        //given
        UUID customerId = UUID.randomUUID();
        String customerName = "name";
        String customerEmail = "email@naver.com";
        Customer customer = new Customer(customerId, customerName, customerEmail);

        UUID voucherId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
            .thenReturn(Optional.of(customer));

        doNothing().when(voucherWalletService).allocateVoucher(customerId, voucherId);

        when(voucherService.existsVoucher(voucherId))
            .thenReturn(true);
        //when

        customerService.allocateVoucher(customerId, voucherId);

        //then
        verify(customerRepository).findById(customerId);
        verify(voucherWalletService).allocateVoucher(customerId, voucherId);
        verify(voucherService).existsVoucher(voucherId);
    }

    @DisplayName("할당 실패 테스트 - 고객이 없으면 예외를 던진다. ")
    @Test
    void allocateVoucherFailThrowsTest() {
        //given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
            .thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class, () ->
            customerService.allocateVoucher(customerId, voucherId));

        //then
        verify(customerRepository).findById(customerId);
    }

    @DisplayName("할당 실패 테스트 - 바우처가없으면 없으면 예외를 던진다. ")
    @Test
    void allocateVoucherFailVoucherNullThrowsTest() {
        //given
        UUID customerId = UUID.randomUUID();
        String customerName = "name";
        String customerEmail = "email@naver.com";
        Customer customer = new Customer(customerId, customerName, customerEmail);

        UUID voucherId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
            .thenReturn(Optional.of(customer));

        when(voucherService.existsVoucher(voucherId))
            .thenReturn(false);
        //when

        assertThrows(EntityNotFoundException.class, () ->
            customerService.allocateVoucher(customerId, voucherId));

        //then
        verify(customerRepository).findById(customerId);
        verify(voucherService).existsVoucher(voucherId);
    }

    @DisplayName("블랙리스트 조회 테스트 - 저장된 모든 블랙리스트가 조회된다.")
    @Test
    void findAllBlockCustomersSuccessTest() {
        //given
        BlockCustomer blockCustomer1 = new BlockCustomer(UUID.randomUUID(), UUID.randomUUID(),
            LocalDateTime.now());

        BlockCustomer blockCustomer2 = new BlockCustomer(UUID.randomUUID(), UUID.randomUUID(),
            LocalDateTime.now());

        List<BlockCustomer> blockCustomers = List.of(blockCustomer1, blockCustomer2);

        when(blockCustomerService.findAll())
            .thenReturn(blockCustomers);

        //when
        List<BlockCustomer> findBlockCustomers = customerService.findAllBlockCustomers();

        //then
        assertEquals(blockCustomers, findBlockCustomers);
        verify(blockCustomerService).findAll();

    }

}