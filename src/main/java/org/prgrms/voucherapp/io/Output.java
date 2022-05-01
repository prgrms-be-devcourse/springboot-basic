package org.prgrms.voucherapp.io;

import org.prgrms.voucherapp.global.enums.ModuleCommand;

public interface Output {
    void informModuleMenu();

    void informCrudCommand(ModuleCommand moduleCommand);

    void informWalletCommand();

    void errorMessage(String msg);

    void exitMessage();

    void cancelMessage();

    void completeMessage(String msg);

    void informVoucherTypeFormat();

    void informCustomerStatus();

    void infoMessage(String msg);
}
