package kr.co.programmers.springbootbasic.io;


import kr.co.programmers.springbootbasic.customer.dto.response.CustomerResponseDto;
import kr.co.programmers.springbootbasic.voucher.dto.response.VoucherResponseDto;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.List;

public interface Output {
    void printProgramMenu();

    void printCreationMenu();

    void printAmountEnterMessage(VoucherType voucherType);

    void printMessage(String message);

    void printVoucherMessage(VoucherResponseDto dto);

    void printVoucherListMessage(List<VoucherResponseDto> list);
    void printCustomerListMessage(List<CustomerResponseDto> list);

    void printExit();
}
