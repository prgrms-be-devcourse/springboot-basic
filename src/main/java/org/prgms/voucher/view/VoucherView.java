package org.prgms.voucher.view;

import org.prgms.voucher.dto.VoucherResponseDto;

import java.util.List;

public interface VoucherView {

    void printOptions();

    void printVouchers(List<VoucherResponseDto> vouchers);

    String readChoice();

    String readVoucherType();

    long readAmount();

    void printError(String message);
}
