package org.prgrms.voucherprgrms.io;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements OutputConsole, InputConsole {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getCommand() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
        System.out.println();
        System.out.println("== Voucher - Customer ==");
        System.out.println("Type **allocate** to allocate a voucher to customer.");
        System.out.println("Type **remove** to remove a voucher from customer.");
        System.out.println();

        return scanner.nextLine();
    }

    @Override
    public long getVoucherValue() {
        System.out.println("Type **value**");
        System.out.println();

        return Long.parseLong(scanner.nextLine());
    }

    @Override
    public String getVoucherType() {
        System.out.println("Select **VoucherType**");
        for (VoucherType type : VoucherType.values()) {
            System.out.println(MessageFormat.format("- {0}", type.getName()));
        }
        System.out.println();

        return scanner.nextLine();
    }

    @Override
    public int getSelect() {
        System.out.println("Select **Number**");
        return Integer.parseInt(scanner.nextLine()) - 1;
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        for (int i = 0; i < voucherList.size(); i++) {
            Voucher voucher = voucherList.get(i);
            //UUID, VoucherTypeName
            System.out.println(MessageFormat.format("({0}) Voucher {1} is {2}", i + 1, voucher.getVoucherId(), voucher.getDTYPE()));
        }
        System.out.println();
    }

    @Override
    public void printCustomerList(List<Customer> customerList) {
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            //UUID, CustomerName
            System.out.println(MessageFormat.format("({0}) Customer {1} is {2}", i + 1, customer.getCustomerId(), customer.getName()));
        }
        System.out.println();
    }

    @Override
    public void printCommandErrorMessage() {
        System.out.println("잘못된 입력입니다.");
    }

    @Override
    public void printSqlErrorMessage() {
        System.out.println("");
    }

    @Override
    public void printFailedAllocation() {
        System.out.println("Can't allocate voucher to this customer");
    }
}
