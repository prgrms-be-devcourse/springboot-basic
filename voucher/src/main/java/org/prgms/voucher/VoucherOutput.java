package org.prgms.voucher;

import org.prgms.voucher.voucher.AmountVoucherController;
import org.prgms.voucher.voucher.VoucherPrintDto;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherOutput {
    private final AmountVoucherController voucherController;

    public VoucherOutput(AmountVoucherController voucherController) {
        this.voucherController = voucherController;
    }

    public String printVoucherLists() {
        List<VoucherPrintDto> voucherPrintDtos = voucherController.listVoucher();
        
        return voucherPrintDtos.stream()
                .map(VoucherPrintDto::generateVoucherPrint)
                .collect(Collectors.joining("\n"));
    }
}
