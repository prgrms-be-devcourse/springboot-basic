package com.prgrms.vouchermanagement.infra.input;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
import com.prgrms.vouchermanagement.infra.utils.MenuType;

import java.io.IOException;

public interface InputProvider {

    MenuType inputMenuType() throws IOException;
    String inputVoucherType() throws IOException;

    int inputVoucherAmount() throws IOException;

    String inputVoucherName() throws IOException;
}
