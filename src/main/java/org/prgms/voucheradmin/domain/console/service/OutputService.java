package org.prgms.voucheradmin.domain.console.service;

import java.util.Arrays;
import java.util.List;

import org.prgms.voucheradmin.domain.console.enums.CommandAboutCustomer;
import org.prgms.voucheradmin.domain.console.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.customer.dto.BlacklistCustomerDto;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
import org.springframework.stereotype.Service;

/**
 *  출력을 담당하는 클래스 입니다.
 */
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
                .forEach(commandAboutVoucher -> voucherCommandListBuilder.append(commandAboutVoucher).append("\n"));

        System.out.print(voucherCommandListBuilder);
    }

    public void showVoucherType() {
        StringBuilder voucherTypeStrBuilder = new StringBuilder();

        voucherTypeStrBuilder.append("\n");
        Arrays.stream(VoucherType.values()).forEach(voucherType -> voucherTypeStrBuilder.append(voucherType).append("\n"));

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

    public void showBlacklist(List<BlacklistCustomerDto> blackListedCustomers) {
        blackListedCustomers.forEach(blackListedCustomer -> System.out.println(blackListedCustomer));
    }

    public void showCommandAboutCustomer() {
        StringBuilder customerCommandListBuilder = new StringBuilder();

        customerCommandListBuilder.append("\n");
        Arrays.stream(CommandAboutCustomer.values())
                .forEach(commandAboutCustomer -> customerCommandListBuilder.append(commandAboutCustomer).append("\n"));

        System.out.println(customerCommandListBuilder);
    }

    public void showCustomer(Customer customer, CommandAboutCustomer commandAboutCustomer) {
        switch (commandAboutCustomer) {
            case CREATE -> System.out.println(customer + " created");
            case UPDATE -> System.out.println(customer + " updated");
            default -> System.out.println(customer);
        }
    }

    public void showCustomerList(List<Customer> customers) {
        customers.forEach(customer -> System.out.println(customer));
    }

    public void showVoucherWallet(VoucherWallet voucherWallet) {
        System.out.println("voucher "+voucherWallet.getVoucherId()+" is allocated to customer "+voucherWallet.getCustomerId());
    }
}
