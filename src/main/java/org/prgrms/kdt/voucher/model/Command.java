package org.prgrms.kdt.voucher.model;

public class Command {

    private static final Menu[] menus = Menu.values();
    private static final String LongMessageError = "Your message is over than 1!";
    private static final String WrongMenuError = "There is no menu named";
    private final String command;

    public Command(String command) {
        if(isLongerThanOne(command)) {
            throw new IllegalArgumentException(LongMessageError);
        }
        if(!isValidMenu(command)) {
            throw new IllegalArgumentException(WrongMenuError);
        }
        this.command = command;
    }

    public boolean isLongerThanOne(String command) {
        String[] parsedCommand = command.
                split(" ");
        return parsedCommand.length >= 2;
    }

    public boolean isValidMenu(String command) {
        for (Menu menu : menus) {
            if (menu.getMenuName().equals(command)) {
                return true;
            }
        }
        return false;
    }
}
