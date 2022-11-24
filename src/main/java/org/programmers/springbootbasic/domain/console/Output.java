package org.programmers.springbootbasic.domain.console;

import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.customer.dto.CustomerOutputDto;

import java.util.List;

public interface Output {
    void printMainMenu();

    void printVouchers(List<Voucher> vouchers);

    void printBlacklist(List<CustomerOutputDto> blacklist);
}
