package org.programers.vouchermanagement.view;

import org.programers.vouchermanagement.voucher.dto.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.VouchersResponse;
import org.programers.vouchermanagement.util.VoucherConverter;

public class OutputView {

    public static void outputCommand() {
        System.out.println("명령어 리스트");
        for (Command value : Command.values()) {
            System.out.println(value.getCommand());
        }
    }

    public static void outputVouchers(VouchersResponse response) {
        for (VoucherResponse voucher : response.getVouchers()) {
            System.out.println(VoucherConverter.toString(voucher));
        }
    }
}
