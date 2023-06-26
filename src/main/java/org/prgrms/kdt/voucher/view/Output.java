package org.prgrms.kdt.voucher.view;

import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.model.VoucherDTO;

import java.util.List;

public interface Output {

    void showMenu(Menu[] menuList);
    void showVoucherList(List<VoucherDTO> voucherDtoList);
    void printError(String errorMessage);
}
