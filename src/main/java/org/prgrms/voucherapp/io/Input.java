package org.prgrms.voucherapp.io;

import org.prgrms.voucherapp.global.enums.*;

import java.util.Optional;
import java.util.UUID;

public interface Input {
    ModuleCommand moduleInput(String prompt);

    CrudCommand crudCommandInput(String prompt);

    WalletCommand walletCommandInput(String prompt);

    VoucherType voucherTypeInput(String prompt);

    long discountAmountInput(VoucherType type, String prompt);

    UUID UUIDInput(String prompt);

    String customerNameInput(String prompt);

    String customerEmailInput(String prompt);

    Optional<CustomerStatus> customerStatusInput(String prompt);
}
