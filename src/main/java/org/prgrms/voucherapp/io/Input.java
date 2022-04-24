package org.prgrms.voucherapp.io;

import org.prgrms.voucherapp.global.enums.*;

import java.util.Optional;
import java.util.UUID;

/*
* Input : 입력 인터페이스
* Q.
* 입력 인터페이스를 정의하는 것의 필요성, 근거가 잘 와닿지 않습니다.
* 필요한 메소드마다 인터페이스에 정의하여 사용하였는데, 이렇게 되면 인터페이스의 다형성 등을 잘 못살리게 되는 것이 아닌지 고민됩니다.
* 입력 인터페이스를 인터페이스로서 잘 사용하려면 어떤 메소드들만 정의하는 것이 좋을까요?
* */
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
