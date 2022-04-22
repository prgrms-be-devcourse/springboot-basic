package com.programmers.part1.ui;

import com.programmers.part1.customer.CustomerService;
import com.programmers.part1.domain.voucher.VoucherType;
import com.programmers.part1.io.Console;
import com.programmers.part1.io.message.Message;
import com.programmers.part1.order.voucher.VoucherService;
import lombok.Builder;

import java.util.UUID;

/**
 * UI름 담당하는 계층
 * */
public class Client implements Runnable {

    private final Console console;
    private final CustomerService customerService;
    private final VoucherService voucherService;

    @Builder
    public Client(Console console, CustomerService customerService, VoucherService voucherService) {
        this.console = console;
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {

        boolean isWorking = true;


        while (isWorking) {
            try {

                console.write(Message.START_PROMPT);

                CommandType requestCommand = CommandType.getCommandType(console.readLine());
                switch (requestCommand) {
                    case CUSTOMER_CREATE -> createCustomer();
                    case CUSTOMER_FIND_LIST -> viewAllCustomers();
                    case VOUCHER_CREATE -> createVoucher();
                    case VOUCHER_FIND_LIST -> viewAllVouchers();
                    case ADD_VOUCHER_TO_CUSTOMER -> addVoucherToCustomer();
                    case LIST_CUSTOMER_BY_VOUCHER -> viewCustomersHavingVoucher();
                    case DELETE_VOUCHER_FROM_CUSTOMER -> deleteVoucherWallet();
                    case LIST_VOUCHER_BY_CUSTOMER -> viewVouchersHavingCustomers();
                    case EXIT -> {
                        console.write(Message.EXIT);
                        isWorking = false;
                    }
                }

            } catch (RuntimeException e) {
                console.write(e.getMessage());
            }
        }
    }


    // 1. 새로운 고객 생성
    private void createCustomer() {
        console.write(Message.CUSTOMER_NAME_PROMPT);
        String name = console.readLine();

        console.write(Message.CUSTOMER_EMAIL_PROMPT);
        String email = console.readLine();

        console.write(customerService.createCustomer(name, email));
        console.write(Message.CREATE_SUCCESS);
    }

    //2. 모든 고객 조회
    private void viewAllCustomers() {
        customerService.getAllCustomer().iterator().forEachRemaining(System.out::println);
        console.write(Message.VIEW_SUCCESS);
    }

    //3. 새로운 바우처 생성
    private void createVoucher() {
        console.write(Message.VOUCHER_PROMPT);
        VoucherType requestType = VoucherType.getVoucherTypeByRequest(console.readLine());

        console.write(Message.AMOUNT_PROMPT);
        int amount = Integer.parseInt(console.readLine());

        console.write(voucherService.createVoucher(requestType, amount));
        console.write(Message.CREATE_SUCCESS);
    }

    //4. 모든 바우처 조회
    private void viewAllVouchers() {
        voucherService.getAllVoucher().iterator().forEachRemaining(System.out::println);
        console.write(Message.VIEW_SUCCESS);
    }

    //5. 고객에게 바우처 할당
    private void addVoucherToCustomer() {
        console.write(Message.CUSTOMER_ID_PROMPT);
        UUID customerId = UUID.fromString(console.readLine().trim());

        console.write(Message.VOUCHER_ID_PROMPT);
        UUID voucherId = UUID.fromString(console.readLine().trim());

        console.write(customerService.createVoucherWallet(customerId, voucherId));
    }

    //6. 바우처를 소유한 고객들
    private void viewCustomersHavingVoucher() {
        console.write(Message.VOUCHER_ID_PROMPT);

        UUID voucherId = UUID.fromString(console.readLine().trim());
        customerService.getCustomersWithVoucher(voucherId)
                .iterator()
                .forEachRemaining(System.out::println);

        console.write(Message.VIEW_SUCCESS);
    }

    //7. 고객에게 할당한 바우처 해제
    private void deleteVoucherWallet() {
        console.write(Message.CUSTOMER_ID_PROMPT);
        UUID customerId = UUID.fromString(console.readLine());

        console.write(Message.VOUCHER_ID_PROMPT);
        UUID voucherId = UUID.fromString(console.readLine());

        customerService.deleteVoucherWalletWithIds(customerId, voucherId);
        console.write(Message.DELETE_SUCCESS);
    }

    //8. 고객이 가지고 있는 바우처 리스트
    private void viewVouchersHavingCustomers() {
        console.write(Message.CUSTOMER_ID_PROMPT);

        UUID customerId = UUID.fromString(console.readLine().trim());
        voucherService.getVouchersWithCustomer(customerId)
                .iterator()
                .forEachRemaining(System.out::println);

        console.write(Message.VIEW_SUCCESS);
    }
}
