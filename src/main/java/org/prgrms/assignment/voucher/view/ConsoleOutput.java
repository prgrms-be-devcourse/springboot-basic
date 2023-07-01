package org.prgrms.assignment.voucher.view;

import org.prgrms.assignment.voucher.dto.VoucherDTO;
import org.prgrms.assignment.voucher.model.Menu;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ConsoleOutput implements Output {

    private static final String DEFAULT_PROGRAM_MESSAGE = "=== Voucher Program ===";
    private static final String DEFAULT_VOUCHER_CATEGORIES = String.format("%-25s%-25s%-25s%-25s"
            ,"VoucherType" ,"VoucherName" ,"Benefit", "CreatedAt");
    private static final String EMPTY_MESSAGE = "This Repository is EMPTY!!";
    public static final String SELECT_VOUCHER_MESSAGE = "TYPE YOUR VOUCHER";

    @Override
    public void showMenu(Menu[] menuList) {
        System.out.println(DEFAULT_PROGRAM_MESSAGE);
        Arrays.stream(menuList)
            .forEach(menu -> System.out.println(menu.getMenuExplain()));
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
        if(voucherDTOList.isEmpty()) {
            System.out.println(EMPTY_MESSAGE);
            return;
        }
        System.out.println(DEFAULT_VOUCHER_CATEGORIES);
        StringBuilder stringBuilder = new StringBuilder();
        for(VoucherDTO voucherDTO : voucherDTOList) {
            stringBuilder.append(String.format("%-25s", voucherDTO.getVoucherType().toString()))
                .append(String.format("%-25s", voucherDTO.getVoucherDTOName()))
                .append(String.format("%-25s", voucherDTO.getBenefit()))
                .append(String.format("%-25s", voucherDTO.getCreatedAt()))
                .append('\n');
        }
        System.out.println(stringBuilder);
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
