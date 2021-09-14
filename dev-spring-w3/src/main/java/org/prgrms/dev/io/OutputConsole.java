package org.prgrms.dev.io;

import org.prgrms.dev.blacklist.domain.Blacklist;
import org.prgrms.dev.voucher.domain.Voucher;

import java.util.List;

public class OutputConsole implements Output {

    @Override
    public void init() {
        StringBuffer sb = new StringBuffer();
        sb.append("=== Voucher Program ===");
        sb.append(System.lineSeparator());
        sb.append("Type [exit] to exit the program.");
        sb.append(System.lineSeparator());
        sb.append("Type [create] to create a new voucher.");
        sb.append(System.lineSeparator());
        sb.append("Type [list] to list all vouchers.");
        System.out.println(sb);
    }

    @Override
    public void selectVoucherType() {
        StringBuffer sb = new StringBuffer();
        sb.append("=== Select Voucher Type ===");
        sb.append(System.lineSeparator());
        sb.append("fixed amount [fixed] | percent discount [percent] ");
        System.out.print(sb);
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        voucherList.stream()
                .map(Voucher::toString)
                .forEach(System.out::println);
    }

    @Override
    public void printInvalidNumber() {
        System.out.println("Invalid number input. Please re-enter.");
    }

    @Override
    public void printInvalidCommandType() {
        System.out.println("Invalid command type input. Please re-enter.");
    }

    @Override
    public void printInvalidVoucherType() {
        System.out.println("Invalid voucher type input. Please re-enter.");
    }

    @Override
    public void printBlackList(List<Blacklist> blackList) {
        blackList.stream()
                .map(Blacklist::toString)
                .forEach(System.out::println);
    }

}
