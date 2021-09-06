package org.prgrms.kdt.io;

import org.prgrms.kdt.blacklist.BlackUser;
import org.prgrms.kdt.voucher.model.Voucher;

import java.util.List;

public interface UserInteraction {

    void showInfoMessage();

    void showVoucherTypeSelectMessage();

    void showVoucherDiscountMessage();

    void showExitProgramMessage();

    void showInvalidMessage();

    void showInvalidTypeMessage();

    void showVoucherList(List<Voucher> list);

    void showBlackList(List<BlackUser> blackUserList);

}
