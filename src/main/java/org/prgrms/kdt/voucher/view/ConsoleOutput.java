package org.prgrms.kdt.voucher.view;

import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.model.VoucherDTO;

import java.util.List;

public class ConsoleOutput implements Output{

    private static final String defaultProgramMessage = "=== Voucher Program ===";
    private static final String defaultVoucherCategories = "VoucherType\t\t" + "VoucherName\t\t" + "Benefit";

    private static void accept(Menu menu) {
        System.out.println(menu.getMenuExplain());
    }

    @Override
    public void showMenu(Menu[] menuList) {
        System.out.println(defaultProgramMessage);
        for(Menu menu : menuList) {
            System.out.println(menu.getMenuExplain());
        }
    }

    @Override
    public void showVoucherList(List<VoucherDTO> voucherDTOList) {
        System.out.println(defaultVoucherCategories);
        for(VoucherDTO voucherDTO : voucherDTOList) {
            System.out.println(voucherDTO);
        }
    }

    @Override
    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
