package org.prgrms.kdt.view;

import org.prgrms.kdt.menu.Menu;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OutputView {

    private static final String PROGRAMNAME = "=== Voucher Program ===";
    private static final String EXIT = "Type exit to exit the program.";
    private static final String CREATE = "Type create to create a new voucher.";
    private static final String LIST = "Type list to list all vouchers.";
    public static void showMenus() {
//        List<String> menuList = Stream.of(Menu.values())
//                .map(Enum::name)
//                .collect(Collectors.toList());
//        System.out.println(menuList);
        System.out.println(PROGRAMNAME);
        System.out.println(EXIT);
        System.out.println(CREATE);
        System.out.println(LIST);
    }
}
