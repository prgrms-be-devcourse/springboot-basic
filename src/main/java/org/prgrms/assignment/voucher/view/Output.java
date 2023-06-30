package org.prgrms.assignment.voucher.view;

import org.prgrms.assignment.voucher.dto.VoucherDTO;
import org.prgrms.assignment.voucher.model.Menu;
import org.prgrms.assignment.voucher.model.VoucherType;

import java.util.List;

public interface Output {

    void showMenu(Menu[] menuList);

    void showVoucherTypes(VoucherType[] voucherTypes);

    void showVoucherList(List<VoucherDTO> voucherDtoList);

    void printError(String errorMessage);

    void printMessage(String message);
}
