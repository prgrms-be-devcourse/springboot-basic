package org.prgrms.kdt.voucher.view;

import org.prgrms.kdt.voucher.dto.VoucherDTO;
import org.prgrms.kdt.voucher.model.Menu;

import java.util.List;
import java.util.Optional;

public class ConsoleOutput implements Output{

    private static final String DEFAULT_PROGRAM_MESSAGE = "=== Voucher Program ===";
    private static final String DEFAULT_VOUCHER_CATEGORIES = "VoucherType\t\t" + "VoucherName\t\t" + "Benefit";
    private static final String EMPTY_MESSAGE = "This Repository is EMPTY!!";

    @Override
    public void showMenu(Menu[] menuList) {
        System.out.println(DEFAULT_PROGRAM_MESSAGE);
        for(Menu menu : menuList) {
            System.out.println(menu.getMenuExplain());
        }
    }

    @Override
    public void showVoucherList(Optional<List<VoucherDTO>> voucherDTOList) {
        System.out.println(DEFAULT_VOUCHER_CATEGORIES);
        if(voucherDTOList.isEmpty()) {
            System.out.println(EMPTY_MESSAGE);
            return;
        }
        Optional.of(voucherDTOList)
                .ifPresent(System.out::println);
    }

    @Override
    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
