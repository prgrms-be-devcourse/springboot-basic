package org.prgrms.voucherapp.io;

import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.global.Menu;
import org.prgrms.voucherapp.global.VoucherType;

public interface Input {
    Menu commandInput(String s) throws WrongInputException;
    VoucherType voucherTypeInput(String s) throws WrongInputException;
}
