package org.prgrms.devcourse.ui;

import org.prgrms.devcourse.domain.BlackUser;
import org.prgrms.devcourse.domain.Voucher;

import java.util.List;

public interface UserInterface {
    String input();

    void showVoucherProgramMenuInterface();

    void showVoucherTypeSelectMessage();

    void showVoucherDiscountValueInputMessage();

    void showVoucherProgramTerminateMessage();

    void showInvalidInputMessage();

    void showVoucherList(List<Voucher> voucherList);

    void showBlackList(List<BlackUser> blackUserList);

}
