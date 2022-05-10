package org.prgrms.kdtspringvoucher.cmdapp.console;

import org.prgrms.kdtspringvoucher.cmdapp.ServiceType;
import org.prgrms.kdtspringvoucher.entity.customer.Customer;
import org.prgrms.kdtspringvoucher.entity.customer.CustomerType;
import org.prgrms.kdtspringvoucher.entity.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringvoucher.entity.voucher.PercentDiscountVoucher;
import org.prgrms.kdtspringvoucher.entity.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Console implements Input, Output {
    private final Scanner sc = new Scanner(System.in);

    public void initialInput() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("exit> 프로그램 종료");
        System.out.println("create> voucher or customer 생성");
        System.out.println("list> voucher or customer 조회");
        System.out.println("delete> voucher or customer 삭제");
        System.out.println("assign> voucher 할당");
        System.out.print("Enter a command: ");
    }

    public void listInput() {
        System.out.println("1> 모든 바우처 조회");
        System.out.println("2> 모든 고객 조회");
        System.out.println("3> 특정 고객의 바우처 조회");
        System.out.println("4> 특정 바우처를 가지고 있는 고객 조회");
        System.out.println("5> black customer 조회");
    }

    @Override
    public ServiceType getServiceType() {
        initialInput();
        String command = sc.next();

        ServiceType serviceType;

        switch (command.toLowerCase()) {
            case "list" -> {
                listInput();
                int listCommand = sc.nextInt();
                switch (listCommand) {
                    case 1 -> serviceType = ServiceType.LIST_OF_ALL_VOUCHERS;
                    case 2 -> serviceType = ServiceType.LIST_OF_ALL_CUSTOMERS;
                    case 3 -> serviceType = ServiceType.LIST_OF_VOUCHERS_BY_CUSTOMER;
                    case 4 -> serviceType = ServiceType.LIST_OF_CUSTOMERS_BY_VOUCHER;
                    case 5 -> serviceType = ServiceType.LIST_OF_BLACK_CUSTOMERS;
                    default -> serviceType = ServiceType.REPLAY;
                }
            }
            case "create" -> {
                System.out.println("1> voucher 생성");
                System.out.println("2> customer 생성");
                int createCommand = sc.nextInt();
                switch (createCommand) {
                    case 1 -> serviceType = ServiceType.CREATE_VOUCHER;
                    case 2 -> serviceType = ServiceType.CREATE_CUSTOMER;
                    default -> serviceType = ServiceType.REPLAY;
                }
            }
            case "delete" -> serviceType = ServiceType.DELETE;
            case "assign" -> serviceType = ServiceType.ASSIGN;
            case "exit" -> serviceType = ServiceType.EXIT;
            default -> serviceType = ServiceType.REPLAY;
        }
        return serviceType;
    }

    public Voucher getVoucher() {
        System.out.println("Create Voucher> Select Voucher Type");
        System.out.println("1> Fixed Voucher");
        System.out.println("2> Percentage Voucher");
        String fixedVoucherNum = "1";
        String percentVoucherNum = "2";
        String voucherType = sc.next();

        if (fixedVoucherNum.equals(voucherType)) {
            System.out.print("Input Fixed Voucher Discount Amount: ");
            long amount = sc.nextLong();
            FixedAmountVoucher fixedAmountVoucher;
            try {
                fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount, LocalDateTime.now());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return null;
            }
            return fixedAmountVoucher;

        } else if (percentVoucherNum.equals(voucherType)) {
            System.out.print("Input Percentage Voucher Discount Percentage: ");
            int percent = sc.nextInt();

            PercentDiscountVoucher percentDiscountVoucher;
            try {
                percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent, LocalDateTime.now());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return null;
            }
            return percentDiscountVoucher;

        } else {
            System.out.println("input 1 or 2");
            return null;
        }
    }

    @Override
    public String inputCustomerId() {
        System.out.println("고객 아이디 입력:");
        return sc.next();
    }

    @Override
    public String inputVoucherId() {
        System.out.println("바우처 아이디 입력:");
        return sc.next();
    }

    public Customer getCustomer() {
        System.out.println(">>>>>>>>>>>>>>Create Custoemr<<<<<<<<<<<<<<<<");
        System.out.println("고객 아이디 입력:");
        String customerName = sc.next();

        System.out.println("고객 이메일 입력:");
        String customerEmail = sc.next();

        System.out.println("고객 블랙리스트로 등록하시겠습니까? y or n");
        String customerType = sc.next();
        String strY = "y";
        if (strY.equals(customerType)) {
            System.out.println(">>>>>this is y");
            return new Customer(UUID.randomUUID(), customerName, customerEmail, LocalDateTime.now(), CustomerType.BLACK);
        }
        System.out.println(">>>>>this is NO y");
        return new Customer(UUID.randomUUID(), customerName, customerEmail, LocalDateTime.now());
    }

    @Override
    public void printList(List list) {
        for (Object value : list) {
            System.out.println(value.toString());
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
