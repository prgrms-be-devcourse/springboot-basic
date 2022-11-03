package com.programmers.voucher;

import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;

import java.io.IOException;


public class CommandLineApplication {

    public static void main(String[] args) throws IOException {
        Output output = new Output();
        Input input = new Input();
        ApplicationManager applicationManager = new ApplicationManager(true);

        output.printDescription();
        while (applicationManager.isRun()) {
            output.printStartOrder();
            String menu = input.input();
            Menu m = Menu.getMenu(menu);

            switch (m) {
                case CREATE:
                    break;
                case LIST:
                    break;
                case  EXIT:
                    applicationManager.setRun(false);
                    output.printTermination();
                    break;
            }
        }
    }

}
