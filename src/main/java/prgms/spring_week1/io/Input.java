package prgms.spring_week1.io;

import prgms.spring_week1.exception.NoSuchOptionValueException;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;
import prgms.spring_week1.menu.Menu;

import java.util.Scanner;

public class Input {
    private final Scanner sc = new Scanner(System.in);

    public String input() {
        return sc.nextLine();
    }

    public Menu selectMenu() {
        String selectOption = this.input();
        return Menu.findMenuType(selectOption);
    }
}
