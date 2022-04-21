package org.prgrms.voucherapp.io;

import org.prgrms.voucherapp.exception.WrongAmountException;
import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.global.enums.Command;
import org.prgrms.voucherapp.global.enums.ModuleCommand;
import org.prgrms.voucherapp.global.enums.VoucherType;

import java.util.UUID;

/*
* Input : 입력 인터페이스
* Q.
* 입력 인터페이스를 정의하는 것의 필요성, 근거가 잘 와닿지 않습니다.
* 필요한 메소드마다 인터페이스에 정의하여 사용하였는데, 이렇게 되면 인터페이스의 다형성 등을 잘 못살리게 되는 것이 아닌지 고민됩니다.
* 입력 인터페이스를 인터페이스로서 잘 사용하려면 어떤 메소드들만 정의하는 것이 좋을까요?
* */
public interface Input {
    ModuleCommand moduleInput(String s) throws WrongInputException;

    Command commandInput(String s) throws WrongInputException;

    VoucherType voucherTypeInput(String s) throws WrongInputException;

    long discountAmountInput(VoucherType type, String s) throws WrongInputException, WrongAmountException;

    UUID customerIdInput(String s) throws WrongInputException;

    UUID voucherIdInput(String s) throws WrongInputException;
}
