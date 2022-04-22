package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.customer.BlackListRepository;
import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.io.Console;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanagement.util.Messages.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VoucherManagementTest {

    @Mock
    Console console;

    @Mock
    VoucherService voucherService;

    @Mock
    BlackListRepository blackListRepository;

    @Test
    @DisplayName("잘못된 메뉴를 입력하면 INPUT_ERROR 메시지가 출력된다.")
    void test() {
        //given
        when(console.inputCommand()).thenReturn("wrong command", "exit");

        //when
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

        //then
        verify(console, times(1)).printMessage(INPUT_ERROR);
    }

    @Test
    @DisplayName("create 메뉴를 선택하면 바우처 생성 프로세스가 실행된다.")
    void createVoucherTest() throws IOException {
        // given
        int amount = 5000;
        int voucherTypeNumber = 1;
        when(console.inputCommand()).thenReturn("create", "exit");
        when(console.inputVoucherType()).thenReturn(voucherTypeNumber);
        when(console.inputDiscount()).thenReturn(amount);

        // when
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

        // then
        verify(console).inputVoucherType();
        verify(console).inputDiscount();
        verify(voucherService).addVoucher(any(), any());
        verify(console).printMessage(SAVE_VOUCHER);
    }

    @Test
    @DisplayName("create 메뉴를 선택후 잘못된 VoucherType을 입력한 경우 INPUT_ERROR 메시지를 출력한다.")
    void createVoucherVoucherTypeInputErrorTest() {
        // given
        int voucherTypeNumber = -1;
        when(console.inputCommand()).thenReturn("create", "exit");
        when(console.inputVoucherType()).thenReturn(voucherTypeNumber);

        // when
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

        // then
        verify(console).printMessage(INPUT_ERROR);
    }

    @Test
    @DisplayName("create 메뉴를 선택후 잘못된 범위의 amount가 입력된 경우 INPUT_ERROR 메시지를 출력한다.")
    void createVoucherAmountInputErrorTest() {
        // given
        int voucherTypeNumber = 1; //FIXED_AMOUNT_VOUCHER
        int amount = -1;
        when(console.inputCommand()).thenReturn("create", "exit");
        when(console.inputVoucherType()).thenReturn(voucherTypeNumber);
        when(console.inputDiscount()).thenReturn(amount);

        // when
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

        // then
        verify(console, times(0)).printMessage(SAVE_VOUCHER);
        verify(console).printMessage(INPUT_ERROR);
    }

    @Test
    @DisplayName("list 메뉴를 선택하여 Voucher 리스트를 출력한다.")
    void voucherListTest() {
        // given
        when(console.inputCommand()).thenReturn("list", "exit");
        Voucher voucher1 = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 2000, LocalDateTime.now());
        Voucher voucher2 = VoucherType.PERCENT_DISCOUNT.constructor(UUID.randomUUID(), 50, LocalDateTime.now());
        Voucher voucher3 = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 20000, LocalDateTime.now());
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);
        when(voucherService.findAllVouchers()).thenReturn(vouchers);

        // when
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

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
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

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
        Customer customer1 = new Customer(UUID.randomUUID(), "aaa", "aaa@gamil.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "bbb", "bbb@gamil.com", LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "ccc", "ccc@gamil.com", LocalDateTime.now());
        List<Customer> blackList = List.of(customer1, customer2, customer3);
        when(blackListRepository.findAll()).thenReturn(blackList);

        // when
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

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
        new VoucherManagement(voucherService, blackListRepository, console, console).run();

        // then
        verify(blackListRepository).findAll();
        verify(console, times(0)).printList(any());
        verify(console).printMessage(BLACK_LIST_EMPTY);
    }
}