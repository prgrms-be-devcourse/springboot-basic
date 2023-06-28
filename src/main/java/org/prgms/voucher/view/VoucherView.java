package org.prgms.voucher.view;

import org.prgms.voucher.dto.BlackCustomerResponseDto;
import org.prgms.voucher.dto.VoucherResponseDto;

import java.util.List;

public interface VoucherView {

    void printOptions();

    void printVouchers(List<VoucherResponseDto> vouchers);

    void printError(String message);

    void printCustomers(List<BlackCustomerResponseDto> customers);

    String readChoice();

    String readVoucherType();

    long readAmount();


}
