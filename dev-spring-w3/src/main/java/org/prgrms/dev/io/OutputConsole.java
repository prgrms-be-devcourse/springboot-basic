package org.prgrms.dev.io;

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
        StringBuffer sb = new StringBuffer();
        sb.append(voucherService.listVoucher().toString());
        System.out.println(sb);
    }

    @Override
    public void invalidNumberInput() {
        StringBuffer sb = new StringBuffer();
        sb.append("Invalid number input. Please re-enter.");
        System.out.println(sb);
    }

    @Override
    public void invalidCommandTypeInput() {
        StringBuffer sb = new StringBuffer();
        sb.append("Invalid command type input. Please re-enter.");
        System.out.println(sb);
    }

    @Override
    public void invalidVoucherTypeInput() {
        StringBuffer sb = new StringBuffer();
        sb.append("Invalid voucher type input. Please re-enter.");
        System.out.println(sb);
    }

}
