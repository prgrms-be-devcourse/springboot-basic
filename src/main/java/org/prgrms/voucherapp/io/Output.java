package org.prgrms.voucherapp.io;

import org.prgrms.voucherapp.global.enums.ModuleCommand;

/*
* Output : 출력 인터페이스
* 출력 인터페이스 또한 입력 인터페이스와 같은 고민입니다.
* */
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
