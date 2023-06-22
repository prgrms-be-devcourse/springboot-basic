package org.devcourse.voucher;


import org.devcourse.voucher.console.Console;

public class CommandLineApplication implements Runnable{
    private final Console console = new Console();

    @Override
    public void run() {
        console.printMenu();
        String option = console.getOption();
        System.out.println(option);
    }
}
