package org.prgrms.kdtspringvoucher.cmdapp;

import org.prgrms.kdtspringvoucher.cmdapp.console.Console;
import org.prgrms.kdtspringvoucher.service.customer.CustomerService;
import org.prgrms.kdtspringvoucher.entity.customer.Customer;
import org.prgrms.kdtspringvoucher.entity.voucher.Voucher;
import org.prgrms.kdtspringvoucher.service.voucher.VoucherService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class cmdapp {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Console console;

    public cmdapp(VoucherService voucherService, CustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    public void run() {
        boolean flag = true;
        while (flag) {
                ServiceType command = console.getServiceType();
            switch (command) {
                case EXIT -> flag = runExit();
                case REPLAY -> runReplay();
                case LIST_OF_ALL_VOUCHERS -> runListOfAllVouchers();
                case LIST_OF_ALL_CUSTOMERS -> runListOfAllCustomers();
                case LIST_OF_VOUCHERS_BY_CUSTOMER -> runListOfVouchersByCustomer();
                case LIST_OF_CUSTOMERS_BY_VOUCHER -> runListOfCustomersByVoucher();
                case LIST_OF_BLACK_CUSTOMERS -> runListOfBlackCustomers();
                case CREATE_VOUCHER -> runCreateVoucher();
                case CREATE_CUSTOMER -> {
                    Customer customer = console.getCustomer();
                    customerService.createCustomer(customer);
                }
                case ASSIGN -> runAssign();
                case DELETE -> runDelete();
                default -> throw new IllegalStateException("Unexpected value: " + command);
            }
        }
    }

    private void runDelete() {
        System.out.println("고객에게 할당된 바우처를 삭제합니다.");
        String customerIdForDelete = console.inputCustomerId();
        String voucherIdForDelete = console.inputVoucherId();
        try {
            voucherService.deleteVoucherFromCustomer(customerIdForDelete, voucherIdForDelete);
        } catch (Exception e) {
            console.printMessage("deleteVoucher exception: " + e);
        }
        console.printMessage("delete voucher success");
    }

    private void runAssign() {
        console.printMessage("특정 고객에게 바우처를 할당합니다");
        String voucherIdForAllocate = console.inputVoucherId();
        String customerIdForAllocate = console.inputCustomerId();

        try {
            voucherService.assignVoucherToCustoemr(customerIdForAllocate, voucherIdForAllocate);
        } catch (Exception e) {
            console.printMessage("allocateVoucherToCustoemr exception: " + e);
            return;
        }
        console.printMessage("allocate success");
    }

    private void runCreateVoucher() {
        Voucher voucher = console.getVoucher();
        if (voucher == null) {
            console.printMessage("바우처 생성 실패. 다시 한번 시도해주세요.");
            return;
        }
        Optional<Voucher> voucherOptional = voucherService.createVoucher(voucher);
        if (voucherOptional.isEmpty()) {
            console.printMessage("바우처 생성 실패. 다시 한번 시도해주세요.");
            return;
        }
        console.printMessage("바우처 생성 성공!");
    }

    private void runListOfBlackCustomers() {
        console.printList(customerService.getBlackCustomers());
    }

    private void runListOfCustomersByVoucher() {
        String voucherId = console.inputVoucherId();
        if (voucherService.validateVoucher(voucherId) == false) {
            console.printMessage("해당 바우처 아이디가 존재하지 않습니다.");
            return;
        }
        console.printList(customerService.getCustomersByVoucher(voucherId));
    }

    private void runListOfVouchersByCustomer() {
        String customerId = console.inputCustomerId();
        if (customerService.isExistCustomer(customerId) == false) {
            console.printMessage("해당 고객 아이디가 존재하지 않습니다.");
            return;
        }
        console.printList(voucherService.getVouchersByCustomer(customerId));
    }

    private void runListOfAllCustomers() {
        console.printList(customerService.getAllCustomers());
    }

    private void runListOfAllVouchers() {
        console.printList(voucherService.getAllVouchers());
    }

    private void runReplay() {
        console.printMessage("다시 입력하시오.");
    }

    private boolean runExit() {
        console.printMessage("bye bye~");
        return false;
    }
}
