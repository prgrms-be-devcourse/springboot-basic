package org.prgms.voucher.ui;

import org.prgms.voucher.voucher.domain.AmountVoucher;
import org.prgms.voucher.voucher.domain.VoucherOptionType;

public interface Output {
    void printList();

    void printPromotionType(PromotionType[] promotionTypes);

    void printCommandType(CommandType[] commandTypes);

    void printOptionType(VoucherOptionType[] voucherOptionTypes);

    void printInitialMoney();

    void printAmount();

    void printCreateVoucher(AmountVoucher voucher);
}
