package org.prgms.voucheradmin.domain.console.service;

import java.util.Arrays;
import java.util.List;

import org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.customer.dto.CustomerDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.springframework.stereotype.Service;

@Service
public class OutputService {

    public void showCommandList() {
        StringBuilder commandListBuilder = new StringBuilder();

        commandListBuilder
                .append("\n=== Voucher Program ===\n")
                .append("Type exit to exit the program.\n")
                .append("Type voucher to CRUD about voucher.\n")
                .append("Type customer to CRUD about customer.\n")
                .append("Type blacklist to list all blacklisted customers.\n")
                .append("command> ");

        System.out.print(commandListBuilder);
    }

    public void showCommandAboutVoucher() {
        StringBuilder voucherCommandListBuilder = new StringBuilder();

        voucherCommandListBuilder.append("\n");
        Arrays.stream(CommandAboutVoucher.values())
                .forEach(commandAboutVoucher -> voucherCommandListBuilder.append(commandAboutVoucher.toString()).append("\n"));

        System.out.print(voucherCommandListBuilder);
    }

    public void showVoucherType() {
        StringBuilder voucherTypeStrBuilder = new StringBuilder();

        voucherTypeStrBuilder.append("\n");
        Arrays.stream(VoucherType.values()).forEach(voucherType -> voucherTypeStrBuilder.append(voucherType.toString()).append("\n"));
        voucherTypeStrBuilder.append("\nvoucher type> ");

        System.out.print(voucherTypeStrBuilder);
    }

    public void showVoucher(Voucher voucher, CommandAboutVoucher commandAboutVoucher) {
        switch (commandAboutVoucher) {
            case CREATE -> System.out.println(voucher + " created");
            case UPDATE -> System.out.println(voucher+" updated");
            default -> System.out.println(voucher);
        }
    }

    public void showVoucherList(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> System.out.println(voucher));
    }

    public void showBlacklist(List<CustomerDto> blackListedCustomers) {
        blackListedCustomers.forEach(blackListedCustomer -> System.out.println(blackListedCustomer));
    }

}
