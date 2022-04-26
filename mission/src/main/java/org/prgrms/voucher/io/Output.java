package org.prgrms.voucher.io;

import org.prgrms.voucher.dto.VoucherDto;

import java.util.List;

public interface Output {

    void printPrompt();

    void printInvalidInputError();

    void printVoucherType();

    void printVoucherDiscountType();

    void printVoucherList(List<VoucherDto.VoucherResponse> data);
}
