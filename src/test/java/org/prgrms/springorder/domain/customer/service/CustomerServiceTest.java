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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockedStatic;
import org.prgrms.springorder.domain.customer.Wallet;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.repository.CustomerRepository;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.prgrms.springorder.global.exception.EntityNotFoundException;

@TestInstance(Lifecycle.PER_CLASS)
class CustomerServiceTest {

    private MockedStatic<VoucherFactory> voucherFactoryMockedStatic;

    @BeforeAll
    public void beforeAll() {
        voucherFactoryMockedStatic = mockStatic(VoucherFactory.class);
    }

    @AfterAll
    public void afterAll() {
        voucherFactoryMockedStatic.close();
    }

    @DisplayName("findAll 테스트 - 고객이 보유한 바우처 지갑을 리턴한다.")
    @Test
    void findAllVouchersSuccessTest() {
        //given
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
        CustomerService customerService = new CustomerService(customerRepository, voucherService,
            blockCustomerService);

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

        when(customerRepository.findByIdWithVouchers(customerId))
            .thenReturn(Optional.of(wallet));
        //when

        Wallet findWallet = customerService.findAllVouchers(customerId);

        //then
        assertEquals(customer, findWallet.getCustomer());
        assertEquals(vouchers, findWallet.getVouchers());

        verify(customerRepository).findByIdWithVouchers(customerId);
    }

    @DisplayName("findAll 테스트 - 지갑이 비어있따면 예외를 던진다.")
    @Test
    void findAllVouchersFailThrowsExceptionTest() {
        //given
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
        CustomerService customerService = new CustomerService(customerRepository, voucherService,
            blockCustomerService);

        UUID customerId = UUID.randomUUID();

        when(customerRepository.findByIdWithVouchers(customerId))
            .thenReturn(Optional.empty());
        //when
        assertThrows(EntityNotFoundException.class,
            () -> customerService.findAllVouchers(customerId));
        //then

        verify(customerRepository).findByIdWithVouchers(customerId);
    }

    @DisplayName("delete 테스트 - 고객이 보유한 바우처를 제거한다")
    @Test
    void deleteVoucherSucessTest() {
        //given
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
        CustomerService customerService = new CustomerService(customerRepository, voucherService,
            blockCustomerService);

        UUID customerId = UUID.randomUUID();
        String customerName = "name";
        String customerEmail = "email@naver.com";
        Customer customer = new Customer(customerId, customerName, customerEmail);

        UUID fixedVoucherId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
            .thenReturn(Optional.of(customer));

        doNothing().when(voucherService)
            .deleteVoucherByCustomerId(fixedVoucherId, customerId);

        customerService.deleteVoucher(customerId, fixedVoucherId);
        //when & then

        verify(customerRepository).findById(customerId);

        verify(voucherService).deleteVoucherByCustomerId(fixedVoucherId, customerId);
    }

    @DisplayName("delete 테스트 - 고객이 없다면 예외를 던진다")
    @Test
    void deleteVoucherFailThrowsExceptionTest() {
        //given
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
        CustomerService customerService = new CustomerService(customerRepository, voucherService,
            blockCustomerService);

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
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
        CustomerService customerService = new CustomerService(customerRepository, voucherService,
            blockCustomerService);

        UUID customerId = UUID.randomUUID();
        String customerName = "name";
        String customerEmail = "email@naver.com";
        Customer customer = new Customer(customerId, customerName, customerEmail);

        UUID voucherId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
            .thenReturn(Optional.of(customer));

        doNothing().when(voucherService).changeCustomerId(voucherId, customerId);
        //when

        customerService.allocateVoucher(customerId, voucherId);

        //then
        verify(customerRepository).findById(customerId);
        verify(voucherService).changeCustomerId(voucherId, customerId);
    }

    @DisplayName("할당 실패 테스트 - 고객이 없으면 예외를 던진다. ")
    @Test
    void allocateVoucherFailThrowsTest() {
        //given
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
        CustomerService customerService = new CustomerService(customerRepository, voucherService,
            blockCustomerService);

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

    @DisplayName("블랙리스트 조회 테스트 - 저장된 모든 블랙리스트가 조회된다.")
    @Test
    void findAllBlockCustomersSuccessTest() {
        //given
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        VoucherService voucherService = mock(VoucherService.class);
        BlockCustomerService blockCustomerService = mock(BlockCustomerService.class);
        CustomerService customerService = new CustomerService(customerRepository,
            voucherService, blockCustomerService);

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

    @DisplayName("블랙리스트 조회 테스트 - 저장된 블랙리스트가 없으면 예외를 던진다. ")
    @Test
    void findAllBlockCustomersFailThrowsExceptionTest() {

    }

    @Test
    void createCustomer() {
    }
}