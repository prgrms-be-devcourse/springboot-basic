package org.prgrms.kdt.assignments;

import org.prgrms.kdt.voucher.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ConsoleCommands {

    Optional<CommandType> inputCommand(String command);

    VoucherData inputVoucher();

    void guide();

    void successfullyCreated();

    void creatingVoucherComment();

    void creatingVoucherAmountComment();

    void commandError();

    void printVoucherList(Map<UUID, Voucher> vouchers);

    void moveToBack();

    void exit();
}
