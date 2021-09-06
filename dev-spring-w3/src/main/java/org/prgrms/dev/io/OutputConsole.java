package org.prgrms.dev.io;

import org.prgrms.dev.customer.domain.Customer;
import org.prgrms.dev.customer.service.CustomerService;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.service.VoucherService;

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
    public void voucherSelectType() {
        StringBuffer sb = new StringBuffer();
        sb.append("=== Select Voucher Type ===");
        sb.append(System.lineSeparator());
        sb.append("fixed amount [fixed] | percent discount [percent] ");
        System.out.print(sb);
    }

    @Override
    public void voucherList(VoucherService voucherService) {
        voucherService.listVoucher().stream()
                .map(Voucher::toString)
                .forEach(System.out::println);
    }

    @Override
    public void invalidNumberInput() {
        System.out.println("Invalid number input. Please re-enter.");
    }

    @Override
    public void invalidCommandTypeInput() {
        System.out.println("Invalid command type input. Please re-enter.");
    }

    @Override
    public void invalidVoucherTypeInput() {
        System.out.println("Invalid voucher type input. Please re-enter.");
    }

    @Override
    public void blackList(CustomerService customerService) {
        customerService.blackList().stream()
                .map(Customer::toString)
                .forEach(System.out::println);
    }

}
