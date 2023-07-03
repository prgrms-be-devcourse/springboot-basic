package kr.co.programmers.springbootbasic.io;


import kr.co.programmers.springbootbasic.customer.dto.CustomerDto;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherDto;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.List;

public interface Output {
    void printProgramMenu();

    void printCreationMenu();

    void printAmountEnterMessage(VoucherType voucherType);

    void printMessage(String message);

    void printVoucherMessage(VoucherDto dto);

    void printVoucherListMessage(List<VoucherDto> list);
    void printCustomerListMessage(List<CustomerDto> list);

    void printExit();
}
