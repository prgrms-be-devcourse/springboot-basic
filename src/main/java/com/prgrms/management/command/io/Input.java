package com.prgrms.management.command.io;

import com.prgrms.management.config.GuideType;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.voucher.dto.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.nextLine();
    }

    public VoucherRequest inputVoucherTypeAndAmount() {
        System.out.println(GuideType.VOUCHER_TYPE.getMESSAGE());
        String voucherType = scanner.nextLine();
        System.out.println(GuideType.DISCOUNT.getMESSAGE());
        String amount = scanner.nextLine();
        return new VoucherRequest(voucherType, amount);
    }

    public VoucherType inputVoucherType() {
        System.out.println(GuideType.VOUCHER_TYPE.getMESSAGE());
        String voucherType = scanner.nextLine();
        return VoucherType.of(voucherType);
    }

    public CustomerRequest inputCustomer() {
        System.out.println(GuideType.CUSTOMER_NAME.getMESSAGE());
        String name = scanner.nextLine();
        System.out.println(GuideType.CUSTOMER_EMAIL.getMESSAGE());
        String email = scanner.nextLine();
        System.out.println(GuideType.CUSTOMER_TYPE.getMESSAGE());
        String customerType = scanner.nextLine();
        return new CustomerRequest(name, email, customerType);
    }

    public UUID inputCustomerId() {
        System.out.println(GuideType.CUSTOMER_ID.getMESSAGE());
        String customerId = scanner.nextLine();
        return UUID.fromString(customerId);
    }

    public UUID inputVoucherId() {
        System.out.println(GuideType.VOUCHER_ID.getMESSAGE());
        String voucherId = scanner.nextLine();
        return UUID.fromString(voucherId);
    }

    public String inputCustomerEmail() {
        System.out.println(GuideType.EMAIL.getMESSAGE());
        String email = scanner.nextLine();
        return email;
    }

    public String inputCustomerName() {
        System.out.println(GuideType.CUSTOMER_NAME.getMESSAGE());
        String name = scanner.nextLine();
        return name;
    }
}
