package com.prgrms.management.command.io;

import com.prgrms.management.config.GuideType;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.voucher.domain.VoucherRequest;
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
        System.out.println(GuideType.VOUCHERTYPE.getMESSAGE());
        String voucherType = scanner.nextLine();
        System.out.println(GuideType.DISCOUNT.getMESSAGE());
        String amount = scanner.nextLine();
        return new VoucherRequest(voucherType,amount);
    }

    public VoucherType inputVoucherType() {
        System.out.println(GuideType.VOUCHERTYPE.getMESSAGE());
        String voucherType = scanner.nextLine();
        return VoucherType.of(voucherType);
    }

    public CustomerRequest inputCustomer() {
        System.out.println(GuideType.CUSTOMERNAME.getMESSAGE());
        String name = scanner.nextLine();
        System.out.println(GuideType.CUSTOMEREMAIL.getMESSAGE());
        String email = scanner.nextLine();
        System.out.println(GuideType.CUSTOMERTYPE.getMESSAGE());
        String customerType = scanner.nextLine();
        return new CustomerRequest(name,email,customerType);
    }

    public UUID inputCustomerId() {
        System.out.println(GuideType.CUSTOMERID.getMESSAGE());
        String customerId = scanner.nextLine();
        return UUID.fromString(customerId);
    }

    public UUID inputVoucherId() {
        System.out.println(GuideType.VOUCHERID.getMESSAGE());
        String voucherId = scanner.nextLine();
        return UUID.fromString(voucherId);
    }
}
