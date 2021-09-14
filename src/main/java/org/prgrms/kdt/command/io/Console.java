package org.prgrms.kdt.command.io;

import org.prgrms.kdt.customer.domain.BannedCustomer;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public void printOnStart() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n=== Voucher Program ===\n");
        stringBuffer.append("Type 'exit' to exit the program.\n");
        stringBuffer.append("Type 'create' to create a new voucher.\n");
        stringBuffer.append("Type 'list' to list all vouchers.\n");
        stringBuffer.append("Type 'blacklist' to list blacklist customers.");

        System.out.println(stringBuffer);
    }

    @Override
    public void printOnExit() {
        System.out.println("Exit Voucher Program.");
    }

    @Override
    public void printVoucherList(List<Voucher> vouchers) {
        System.out.println("Print All Voucher List.");
        vouchers.forEach((voucher) -> System.out.println(voucher.toString()));
    }

    @Override
    public void printRequestVoucherType() {
        System.out.println("Input Voucher Type(PERCENT/FIXED) : ");
    }

    @Override
    public void printRequestVoucherValue(VoucherType type) {
        System.out.println(MessageFormat.format("Input discount {0} : ", type));
    }

    @Override
    public void printBlackList(List<BannedCustomer> blackList) {
        System.out.println("Print All BlackList Customers.");
        blackList.forEach((bannedCustomer) -> System.out.println(bannedCustomer.toString()));
    }

    @Override
    public void printInputError() {
        System.out.println("Invalid Input Error. Please type correct command.");
    }

    @Override
    public String inputString(final String inputPrompt) {
        System.out.print(inputPrompt);
        return this.SCANNER.nextLine();
    }
}
