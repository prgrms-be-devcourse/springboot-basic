package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.customer.BlackListRepository;
import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerRepository;
import com.prgrms.vouchermanagement.customer.CustomerService;
import com.prgrms.vouchermanagement.io.Console;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import com.prgrms.vouchermanagement.wallet.VoucherWalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanagement.command.WalletCommand.*;
import static com.prgrms.vouchermanagement.util.Messages.*;
import static com.prgrms.vouchermanagement.voucher.VoucherType.FIXED_DISCOUNT;
import static com.prgrms.vouchermanagement.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VoucherManagementTest {

    @Mock
    Console console;

    @Mock
    VoucherService voucherService;

    @Mock
    BlackListRepository blackListRepository;

    @Mock
    VoucherWalletService voucherWalletService;

    @Mock
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    @DisplayName("잘못된 메뉴를 입력하면 INPUT_ERROR 메시지가 출력된다.")
    void test() {
        //given
        when(console.inputCommand()).thenReturn("wrong command", "exit");

        //when
        runApplication();

        //then
        verify(console, times(1)).printMessage(INPUT_ERROR);
    }

    @Test
    @DisplayName("voucher 메뉴를 선택하면 바우처 생성 프로세스가 실행된다.")
    void createVoucherTest() throws IOException {
        // given
        int amount = 5000;
        int voucherTypeNumber = 1;
        when(console.inputCommand()).thenReturn("voucher", "exit");
        when(console.inputVoucherType()).thenReturn(voucherTypeNumber);
        when(console.inputNumber(any())).thenReturn(amount);

        // when
        runApplication();

        // then
        verify(console).inputVoucherType();
        verify(console).inputNumber(any());
        verify(voucherService).addVoucher(any(), anyLong());
        verify(console).printMessage(SAVE_VOUCHER);
    }

    @Test
    @DisplayName("voucher 메뉴를 입력후 잘못된 VoucherType을 입력한 경우 INPUT_ERROR 메시지를 출력한다.")
    void createVoucherVoucherTypeInputErrorTest() {
        // given
        int voucherTypeNumber = -1;
        when(console.inputCommand()).thenReturn("voucher", "exit");
        when(console.inputVoucherType()).thenReturn(voucherTypeNumber);

        // when
        runApplication();

        // then
        verify(console).printMessage(INPUT_ERROR);
    }

    @Test
    @DisplayName("voucher 메뉴를 입력후 잘못된 범위의 amount가 입력된 경우 INPUT_ERROR 메시지를 출력한다.")
    void createVoucherAmountInputErrorTest() {
        // given
        int voucherTypeNumber = 1; //FIXED_AMOUNT_VOUCHER
        int amount = -1;
        when(console.inputCommand()).thenReturn("voucher", "exit");
        when(console.inputVoucherType()).thenReturn(voucherTypeNumber);
        when(console.inputNumber(anyString())).thenReturn(amount);
        doThrow(IllegalArgumentException.class).when(voucherService).addVoucher(FIXED_DISCOUNT, amount);


        // when
        runApplication();

        // then
        verify(console, times(0)).printMessage(SAVE_VOUCHER);
        verify(console, times(1)).printMessage(INPUT_ERROR);
    }

    @Test
    @DisplayName("list 메뉴를 선택하여 Voucher 리스트를 출력한다.")
    void voucherListTest() {
        // given
        when(console.inputCommand()).thenReturn("list", "exit");
        Voucher voucher1 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 2000, LocalDateTime.now());
        Voucher voucher2 = PERCENT_DISCOUNT.constructor(UUID.randomUUID(), 50, LocalDateTime.now());
        Voucher voucher3 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 20000, LocalDateTime.now());
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);
        when(voucherService.findAllVouchers()).thenReturn(vouchers);

        // when
        runApplication();

        // then
        verify(voucherService).findAllVouchers();
        verify(console).printList(vouchers);
    }

    @Test
    @DisplayName("list 메뉴를 선택했는데 저장된 Voucher가 없는 경우 VOUCHER_LIST_EMPTY 메시지를 출력한다.")
    void voucherEmptyListTest() {
        // given
        when(console.inputCommand()).thenReturn("list", "exit");
        when(voucherService.findAllVouchers()).thenReturn(Collections.emptyList());

        // when
        runApplication();

        // then
        verify(voucherService).findAllVouchers();
        verify(console, times(0)).printList(any());
        verify(console).printMessage(VOUCHER_LIST_EMPTY);
    }

    @Test
    @DisplayName("blacklist 메뉴를 선택하여 black list 고객 이름을 출력한다.")
    void blackListTest() {
        // given
        when(console.inputCommand()).thenReturn("blacklist", "exit");
        Customer customer1 = Customer.of(UUID.randomUUID(), "aaa", "aaa@gamil.com", LocalDateTime.now());
        Customer customer2 = Customer.of(UUID.randomUUID(), "bbb", "bbb@gamil.com", LocalDateTime.now());
        Customer customer3 = Customer.of(UUID.randomUUID(), "ccc", "ccc@gamil.com", LocalDateTime.now());
        List<Customer> blackList = List.of(customer1, customer2, customer3);
        when(blackListRepository.findAll()).thenReturn(blackList);

        // when
        runApplication();

        // then
        verify(blackListRepository).findAll();
        verify(console).printList(blackList);
    }

    @Test
    @DisplayName("blacklist 메뉴 선택했는데 black list 고객이 없는 경우 BLACK_LIST_EMPTY 메시지를 출력한다.")
    void blackListEmptyTest() {
        // given
        when(console.inputCommand()).thenReturn("blacklist", "exit");
        when(blackListRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        runApplication();

        // then
        verify(blackListRepository).findAll();
        verify(console, times(0)).printList(any());
        verify(console).printMessage(BLACK_LIST_EMPTY);
    }

    @Test
    @DisplayName("customer 메뉴를 입력하면 customer 생성")
    void createCustomerTest() {
        // given
        String email = "aaa@gmail.com";
        String name = "aaa";
        when(console.inputCommand()).thenReturn("customer", "exit");
        when(console.inputString(anyString())).thenReturn(name, email);

        // when
        runApplication();

        // then
        verify(customerService).addCustomer(name, email);
        verify(console).printMessage("Customer is saved");
    }

    @Test
    @DisplayName("customer를 입력후 중복된 이메일을 입력하면 Duplicate email 메시지를 출력한다. ")
    void createCustomerDuplicateEmailTest() {
        // given
        String name = "aaa";
        String duplicateEmail = "duplicate@gmail.com";
        when(console.inputCommand()).thenReturn("customer", "exit");
        when(console.inputString(anyString())).thenReturn(name, duplicateEmail);
        doThrow(IllegalArgumentException.class).when(customerService).addCustomer(name, duplicateEmail);

        // when
        runApplication();

        // then
        verify(console, times(0)).printMessage("Customer is saved");
        verify(console).printMessage("Duplicate email");
    }

    @Test
    @DisplayName("잘못된 wallet menu가 입력되면 input error 메시지를 출력한다.")
    void inputWrongWalletMenuTest() {
        // given

        //메뉴 선택
        String wrongMenu = "wrong menu";
        when(console.inputCommand()).thenReturn("wallet", "exit");
        doThrow(InputMismatchException.class).when(console).inputNumber(anyString()); //wallet 메뉴 선택

        // when
        runApplication();

        // then
        verify(console).printMessage(INPUT_ERROR);
    }

    @Test
    @DisplayName("customer의 wallet에 voucher를 추가한다.")
    void addVoucherToCustomerWalletTest() {
        // given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        //메뉴 입력
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(ADD_VOUCHER.getOrder());

        //customerId와 voucherId 입력
        when(console.inputUUID(anyString())).thenReturn(customerId, voucherId);

        // when
        runApplication();

        // then
        verify(voucherWalletService).addVoucherToWallet(customerId, voucherId);
        verify(console).printMessage(MessageFormat.format("{0} is Saved to {1}''s wallet", voucherId, customerId));
    }

    @Test
    @DisplayName("Customer에 Voucher를 추가할 때 customer, voucher의 ID가 UUID 형식이 아닌 다른 값이 들어오면 에러 메시지 출력")
    void addVoucherToCustomerWalletTypeMismatchTest() {
        // given

        //메뉴 입력
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(ADD_VOUCHER.getOrder());

        //UUID 형식이 아닌 값이 입력되면 InputMismatchException이 던져진다.
        doThrow(InputMismatchException.class).when(console).inputUUID(anyString());

        // when
        runApplication();

        // then
        verify(console).printMessage("Please input in UUID format");
    }

    @Test
    @DisplayName("Customer의 Wallet에 Voucher를 추가할 때, 존재하지 않는 VoucherId, CustomerId가 입력되면 에러 메시지를 출력한다.")
    void addVoucherToCustomerWalletNotExistsVoucherTest() {
        // given
        UUID notExistsCustomerId = UUID.randomUUID();
        UUID notExistsVoucherId = UUID.randomUUID();

        //메뉴 입력
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(ADD_VOUCHER.getOrder());

        when(console.inputUUID(anyString())).thenReturn(notExistsCustomerId, notExistsVoucherId);
        doThrow(IllegalArgumentException.class).when(voucherWalletService).addVoucherToWallet(notExistsCustomerId, notExistsVoucherId);

        // when
        runApplication();

        // then
        verify(console).printMessage("Customer or voucher is not registered");
    }

    @Test
    @DisplayName("Customer의 Wallet에 Voucher를 추가할 때, 존재하지 않는 가 입력되면 에러 메시지를 출력한다.")
    void addVoucherToCustomerWalletNotExistsCustomerTest() {
        // given
        UUID notExistsCustomerId = UUID.randomUUID();
        UUID existsVoucherId = UUID.randomUUID();

        //메뉴 입력
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(ADD_VOUCHER.getOrder());

        when(console.inputUUID(anyString())).thenReturn(notExistsCustomerId, existsVoucherId);
        doThrow(IllegalArgumentException.class).when(voucherWalletService).addVoucherToWallet(notExistsCustomerId, existsVoucherId);

        // when
        runApplication();

        // then
        verify(console).printMessage("Customer or voucher is not registered");
    }

    @Test
    @DisplayName("Customer가 가지고있는 Voucher를 조회한다.")
    void findVoucherByCustomerTest() {
        // given
        UUID customerId = UUID.randomUUID();
        Voucher voucher1 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 2000, LocalDateTime.now());
        Voucher voucher2 = PERCENT_DISCOUNT.constructor(UUID.randomUUID(), 50, LocalDateTime.now());
        Voucher voucher3 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 20000, LocalDateTime.now());
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_VOUCHERS.getOrder());

        when(console.inputUUID(anyString())).thenReturn(customerId);
        when(voucherWalletService.findVoucherByCustomer(customerId)).thenReturn(vouchers);

        // when
        runApplication();

        // then

        //에러 메시지가 호출되지 않는다.
        verify(console, times(0)).printMessage("Please input in UUID format");
        verify(console, times(0)).printMessage("This customer is not registered");
        verify(console, times(0)).printMessage(customerId + " has no voucher");

        verify(console).printList(vouchers);
    }

    @Test
    @DisplayName("Customer가 가지고 있는 Voucher를 조회하는데 입력된 customerId가 UUID 형식이 아니면 에러 메시지를 출력한다")
    void findVoucherByCustomerNotUUIDTest() {
        // given

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_VOUCHERS.getOrder());

        //UUID 형식이 입력되지 않으면 InputMismatchException 발생
        when(console.inputUUID(anyString())).thenThrow(InputMismatchException.class);

        // when
        runApplication();

        // then
        verify(console).printMessage("Please input in UUID format");

        //정상 로직은 호출되지 않는다.
        verify(voucherWalletService, times(0)).findVoucherByCustomer(any());
    }

    @Test
    @DisplayName("Customer가 가지고 있는 voucher를 조회하는데 입력된 customerId가 존재하지 않는 id이면 에러 메시지를 출력한다.")
    void findVoucherByCustomerNotExistsIdTest() {
        // given
        UUID notExistsCustomerId = UUID.randomUUID();

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_VOUCHERS.getOrder());

        when(console.inputUUID(anyString())).thenReturn(notExistsCustomerId);
        when(voucherWalletService.findVoucherByCustomer(notExistsCustomerId)).thenThrow(IllegalArgumentException.class);

        // when
        runApplication();

        // then
        verify(console).printMessage("This customer is not registered");
    }

    @Test
    @DisplayName("Cusotmer가 가지고 있는 Voucher를 조회하는데 소유한 Voucher가 없는 경우 에러 메시지를 출력한다.")
    void findVoucherCustomerNoVoucherTest() {
        // given
        List<Voucher> emptyVoucher = Collections.emptyList();
        UUID customerId = UUID.randomUUID();

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_VOUCHERS.getOrder());

        when(console.inputUUID(anyString())).thenReturn(customerId);
        when(voucherWalletService.findVoucherByCustomer(customerId)).thenReturn(emptyVoucher);

        // when
        runApplication();

        // then
        verify(console).printMessage(MessageFormat.format("{0} has no voucher", customerId));
        verify(console, times(0)).printList(emptyVoucher);
    }

    @Test
    @DisplayName("특정 Voucher를 가지고 있는 모든 Customer를 조회한다.")
    void findCustomerByVoucher() {
        // given

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_CUSTOMER.getOrder());

        UUID voucherId = UUID.randomUUID();
        Customer customer1 = Customer.of(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        Customer customer2 = Customer.of(UUID.randomUUID(), "aaa", "bbb@gmail.com", LocalDateTime.now());
        Customer customer3 = Customer.of(UUID.randomUUID(), "aaa", "ccc@gmail.com", LocalDateTime.now());
        List<Customer> customers = List.of(customer1, customer2, customer3);

        when(console.inputUUID(anyString())).thenReturn(voucherId);
        when(voucherWalletService.findCustomerByVoucher(voucherId)).thenReturn(customers);

        // when
        runApplication();

        // then
        verify(console).printList(customers);
    }

    @Test
    @DisplayName("특정 Voucher를 가지고 있는 모든 Customer를 조회하는데 입력된 voucherId가 UUID 형식이 아니면 에러 메시지를 출력한다")
    void findCustomerByVoucherNotUUIDTest() {
        // given

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_CUSTOMER.getOrder());

        //UUID 형식이 입력되지 않으면 InputMismatchException 발생
        when(console.inputUUID(anyString())).thenThrow(InputMismatchException.class);

        // when
        runApplication();

        // then
        verify(console).printMessage("Please input in UUID format");

        //정상 로직은 호출되지 않는다.
        verify(voucherWalletService, times(0)).findVoucherByCustomer(any());
    }

    @Test
    @DisplayName("특정 Voucher를 가지고 있는 모든 Customer를 조회하는데 입력된 voucherId 존재하지 않는 id이면 에러 메시지를 출력한다.")
    void findCustomerByVoucherNotExistsIdTest() {
        // given

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_CUSTOMER.getOrder());

        UUID notExistsVoucherId = UUID.randomUUID();
        when(console.inputUUID(anyString())).thenReturn(notExistsVoucherId);
        when(voucherWalletService.findCustomerByVoucher(notExistsVoucherId)).thenThrow(IllegalArgumentException.class);

        // when
        runApplication();

        // then
        verify(console).printMessage("This voucher is not registered");
    }

    @Test
    @DisplayName("해당 Voucher를 소유한 Customer가 없는 경우 관련 메시지를 출력한다.")
    void findCustomerByVoucherEmptyTest() {
        // given
        List<Customer> emptyCustomer = Collections.emptyList();
        UUID voucherId = UUID.randomUUID();

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(FIND_CUSTOMER.getOrder());

        when(console.inputUUID(anyString())).thenReturn(voucherId);
        when(voucherWalletService.findCustomerByVoucher(voucherId)).thenReturn(emptyCustomer);

        // when
        runApplication();

        // then
        verify(console).printMessage("any customer has no this voucher");
        verify(console, times(0)).printList(anyList());
    }

    @Test
    @DisplayName("Wallet을 삭제한다.")
    void removeWalletTest() {
        // given
        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(REMOVE_VOUCHER.getOrder());

        UUID walletId = UUID.randomUUID();

        when(console.inputUUID(anyString())).thenReturn(walletId);

        // when
        runApplication();

        // then
        verify(voucherWalletService).removeVoucherInWallet(walletId);
        verify(console).printMessage("Voucher in this wallet is removed");
    }

    @Test
    @DisplayName("Wallet을 삭제하는데 walletId가 UUID 형식이 아닌 경우 에러 메시지를 출력한다.")
    void removeWalletNotUUIDTest() {
        // given

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(REMOVE_VOUCHER.getOrder());

        //UUID 형식이 입력되지 않으면 InputMismatchException 발생
        when(console.inputUUID(anyString())).thenThrow(InputMismatchException.class);

        // when
        runApplication();

        // then
        verify(console).printMessage("Please input in UUID format");

        //삭제 로직은 호출되지 않는다.
        verify(voucherWalletService, times(0)).removeVoucherInWallet(any(UUID.class));
    }

    @Test
    @DisplayName("등록되지 않은 Wallet을 삭제하려는 경우 관련 메시지를 출력한다.")
    void removeWalletNotExistsIdTest() {
        // given

        //메뉴 선택
        when(console.inputCommand()).thenReturn("wallet", "exit");
        when(console.inputNumber(anyString())).thenReturn(REMOVE_VOUCHER.getOrder());

        when(console.inputUUID(anyString())).thenReturn(UUID.randomUUID());
        doThrow(IllegalArgumentException.class).when(voucherWalletService).removeVoucherInWallet(any(UUID.class));

        // when
        runApplication();

        // then
        verify(console).printMessage("This wallet is not registered");
    }

    private void runApplication() {
        new VoucherManagement(voucherService, voucherWalletService, blackListRepository, customerService, console, console).run();
    }
}