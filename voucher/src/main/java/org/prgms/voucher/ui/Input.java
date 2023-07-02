package org.prgms.voucher.ui;

import org.prgms.voucher.voucher.domain.VoucherOptionType;

import java.io.IOException;

public interface Input {
    PromotionType getPromotionType() throws IOException;

    CommandType getCommandType() throws IOException;

    VoucherOptionType getOptionType() throws IOException;

    int getInitialMoney() throws IOException;

    int getAmount() throws IOException;
}
