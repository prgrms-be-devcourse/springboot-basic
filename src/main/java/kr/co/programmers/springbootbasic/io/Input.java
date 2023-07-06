package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.io.enums.*;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.UUID;

public interface Input {
    EntireServiceCommand readEntireServiceCommand();

    VoucherType readVoucherType();

    long readAmount();

    VoucherServiceCommand readVoucherCommand();

    CustomerServiceCommand readCustomerServiceCommand();

    String readCustomerName();

    CustomerFindCommand readCustomerFindCommand();

    UUID readUUID();

    CustomerStatus readCustomerStatus();

    WalletServiceCommand readWalletServiceCommand();
}
