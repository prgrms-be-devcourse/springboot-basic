package org.programmers.springbootbasic.io;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Console implements Input, Output{

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public VoucherMainMenuCommand getVoucherMainMenuInput(String inputMessage) throws IOException {
        System.out.print(inputMessage);
        return VoucherMainMenuCommand.valueOfCommand(br.readLine());
    }

    @Override
    public void printMenu(String menu) {
        System.out.println(menu);
    }
}
