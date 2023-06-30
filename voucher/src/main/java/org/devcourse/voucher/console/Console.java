package org.devcourse.voucher.console;

import java.util.Scanner;

public class Console implements Input, Output {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void printMenu() {
        String menuString = """
                == Voucher Program ==
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list ot list all vouchers.
                """.trim();
        System.out.println(menuString);
    }

    @Override
    public Command inputCommand() {
        String commandInput = sc.next();
        Command command = null;
        switch (commandInput) {
            case "exit":
                command = Command.EXIT;
                break;
            case "create":
                command = Command.CREATE;
                break;
            case "LIST":
                command = Command.LIST;
                break;
        }

        return command;
    }

    @Override
    public void printCommandMessage(Command command) {
        switch (command) {
            case CREATE:
                System.out.println("Create Voucher");
                break;
            case LIST:
                System.out.println("List Voucher");
                break;
            case EXIT:
                System.out.println("Terminate Application");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
    }
}
