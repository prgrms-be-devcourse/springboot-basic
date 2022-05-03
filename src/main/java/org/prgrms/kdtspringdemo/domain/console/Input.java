package org.prgrms.kdtspringdemo.domain.console;


import org.prgrms.kdtspringdemo.domain.customer.type.CustomerDMLType;
import org.prgrms.kdtspringdemo.domain.mapping.type.MappingType;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherDMLMenuType;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;

import java.util.Arrays;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Input {
    private static final Logger logger = LoggerFactory.getLogger(Input.class);
    private Scanner scanner = new Scanner(System.in);

    // Menu 입력을 받는다
    public Menu inputMenu() {
        String inputMenu = scanner.nextLine();
        return Menu.of(inputMenu);
    }

    // VoucherType 입력을 받는다.
    public VoucherType inputVoucherType() {
        String inputVoucherType = scanner.nextLine();
        return VoucherType.of(inputVoucherType);
    }

    // VoucherType 입력을 받는다.
    public CustomerDMLType inputCustomerDMLType() {
        String inputCustomerDMLType = scanner.nextLine();
        return CustomerDMLType.of(inputCustomerDMLType);
    }

    // mapiingType 일력을 받는다.
    public MappingType inputMappingType(){
        String inputMappingType = scanner.nextLine();
        return MappingType.of(inputMappingType);
    }

    // VoucherType 에 맞게 할인 금액을 입력 받는다.
    public int inputDiscountAmount(VoucherType voucherType) {
        int discountAmount = 0;

        try {
            discountAmount = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            logger.error("정수가 아닌 값을 입력하였습니다.");
        }

        return discountAmount;
    }

    // VoucherManagement 관리 type 입력
    public VoucherDMLMenuType inputVoucherManagementDMLType() {
        String inputCustomerDMLType = scanner.nextLine();
        return VoucherDMLMenuType.of(inputCustomerDMLType);
    }

    public int inputNumber() {
        int number = 0;

        try{
            number = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            logger.error("정수가 아닌 값을 입력하였습니다.");
        }

        return number;
    }

    public void closeScanner() {
        logger.info("Scanner 을 close 합니다.");
        scanner.close();
    }
}
