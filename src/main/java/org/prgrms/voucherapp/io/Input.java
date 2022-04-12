package org.prgrms.voucherapp.io;

import org.prgrms.voucherapp.exception.WrongAmountException;
import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.global.Command;
import org.prgrms.voucherapp.global.VoucherType;

import java.util.InputMismatchException;

/*commandInput과 voucherTypeInput은 interface의 의미를 못살린 느낌, 맞는지?*/
public interface Input {
    Command commandInput(String s) throws WrongInputException;
    VoucherType voucherTypeInput(String s) throws WrongInputException;
    long discountAmountInput(VoucherType type, String s) throws WrongInputException, WrongAmountException;
}
