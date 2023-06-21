package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.model.Menu;
import org.beryx.textio.InputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.List;
import java.util.Scanner;

public class InputConsole {

    private TextIO input = TextIoFactory.getTextIO();

    public Menu inputMenu() {
        Menu menu = input.newEnumInputReader(Menu.class)
                .read("=== Voucher Program ===");
//        System.out.println();
//        System.out.println("Type exit to exit the program.");
//        System.out.println("Type create to create a new voucher.");
//        System.out.println("Type list to list all vouchers.");
//        System.out.println("=======================");
//        System.out.print("Menu Type : ");
        return menu;
    }

    public String inputVoucher() {
        return "";
    }
}
