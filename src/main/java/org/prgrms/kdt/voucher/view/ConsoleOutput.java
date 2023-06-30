package org.prgrms.kdt.voucher.view;

import org.prgrms.kdt.voucher.dto.VoucherDTO;
import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ConsoleOutput implements Output{

    private static final String DEFAULT_PROGRAM_MESSAGE = "=== Voucher Program ===";
    private static final String DEFAULT_VOUCHER_CATEGORIES = String.format("%-25s %-25s %-25s"
            ,"VoucherType" ,"VoucherName" ,"Benefit");
    private static final String EMPTY_MESSAGE = "This Repository is EMPTY!!";
    public static final String SELECT_VOUCHER_MESSAGE = "TYPE YOUR VOUCHER";

    @Override
    public void showMenu(Menu[] menuList) {
        System.out.println(DEFAULT_PROGRAM_MESSAGE);
        for(Menu menu : menuList) {
            System.out.println(menu.getMenuExplain());
        }
    }

    @Override
    public void showVoucherTypes(VoucherType[] voucherTypes) {
        for(VoucherType voucherType : voucherTypes) {
            System.out.print(voucherType + " / ");
        }
        System.out.println();
    }


    @Override
    public void showVoucherList(List<VoucherDTO> voucherDTOList) {
        System.out.println(DEFAULT_VOUCHER_CATEGORIES);
        if(voucherDTOList.isEmpty()) {
            System.out.println(EMPTY_MESSAGE);
            return;
        }
        voucherDTOList.forEach(System.out::println);
    }

    @Override
    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }


}
