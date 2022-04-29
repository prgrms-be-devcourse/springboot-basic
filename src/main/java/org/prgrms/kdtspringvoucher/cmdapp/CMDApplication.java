package org.prgrms.kdtspringvoucher.cmdapp;

import org.prgrms.kdtspringvoucher.cmdapp.console.Console;
import org.prgrms.kdtspringvoucher.customer.entity.CustomerService;
import org.prgrms.kdtspringvoucher.customer.entity.Customer;
import org.prgrms.kdtspringvoucher.voucher.entity.Voucher;
import org.prgrms.kdtspringvoucher.voucher.entity.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class CMDApplication {
    private static final Logger logger = LoggerFactory.getLogger(CMDApplication.class);
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Console console;
    private final Scanner sc = new Scanner(System.in);

    public CMDApplication(VoucherService voucherService, CustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    public void run() {
        boolean flag = true;
        while (flag) {
                ServiceType command = console.getServiceType();
                switch (command) {
                    case EXIT:
                        flag = false;
                        break;
                    case REPLAY:
                        System.out.println("다시 입력하시오.");
                        break;
                    case LIST_OF_ALL_VOUCHERS:
                        logger.info("Run Voucher List");
                        console.printList(voucherService.getAllVouchers());
                        break;
                    case LIST_OF_ALL_CUSTOMERS:
                        logger.info("Run customer List");
                        console.printList(customerService.getAllCustomers());
                        break;
                    case LIST_OF_VOUCHERS_BY_CUSTOMER:
                        System.out.println("고객 아이디 입력:");
                        String customerId = sc.next();
                        if (customerService.validateCustomer(customerId) == false) {
                            System.out.println("해당 고객 아이디가 존재하지 않습니다.");
                            break;
                        }
                        console.printList(voucherService.getVouchersByCustomer(customerId));
                        break;
                    case LIST_OF_CUSTOMERS_BY_VOUCHER:
                        System.out.println("바우처 아이디 입력:");
                        String voucherId = sc.next();
                        if (voucherService.validateVoucher(voucherId) == false) {
                            System.out.println("해당 바우처 아이디가 존재하지 않습니다.");
                            break;
                        }
                        console.printList(customerService.getCustomersByVoucher(voucherId));
                        break;
                    case LIST_OF_BLACK_CUSTOMERS:
                        console.printList(customerService.getBlackCustomers());
                        break;
                    case CREATE_VOUCHER:
                        Voucher voucher = console.getVoucher();
                        if (voucher == null) {
                            break;
                        }
                        Optional<Voucher> voucherOptional = voucherService.createVoucher(voucher);
                        if (voucherOptional.isEmpty()) {
                            System.out.println("바우처 생성 실패. 다시 한번 시도해주세요.");
                        }
                        System.out.println("바우처 생성 성공!");
                        break;
                    case CREATE_CUSTOMER:
                        Customer customer = console.getCustomer();
                        customerService.createCustomer(customer);
                        break;
                    case ASSIGN:
                        System.out.println("특정 고객에게 바우처를 할당합니다");
                        System.out.println("할당할 바우처 아이디 입력하시오:");
                        String voucherIdForAllocate = sc.next();
                        System.out.println("바우처를 할당할 고객 아이디를 입력하시오:");
                        String customerIdForAllocate = sc.next();
                        logger.info("allocate");
                        try {
                            voucherService.assignVoucherToCustoemr(customerIdForAllocate, voucherIdForAllocate);
                        } catch (Exception e) {
                            System.out.println("allocateVoucherToCustoemr exception: " + e);
                        }
                        System.out.println("allocate success");
                        break;
                    case DELETE:
                        System.out.println("고객에게 할당된 바우처를 삭제합니다.");
                        System.out.println("고객 아이디 입력: ");
                        String customerIdForDelete = sc.next();
                        System.out.println("바우처 아이디 입력: ");
                        String voucherIdForDelete = sc.next();
                        try {
                            voucherService.deleteVoucher(customerIdForDelete, voucherIdForDelete);
                        } catch (Exception e) {
                            System.out.println("deleteVoucher exception: " + e);
                        }
                        System.out.println("delete voucher success");
                        break;
                }
        }
    }
}
