package prgms.spring_week1.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.io.message.ConsoleOutputMessage;
import prgms.spring_week1.menu.Menu;
import prgms.spring_week1.menu.VoucherMenu;

import java.util.Scanner;

public class Input {
    private final Logger logger = LoggerFactory.getLogger(Input.class);
    private final Scanner sc = new Scanner(System.in);

    public void printConsoleMessage(String message) {
        System.out.println(message);
    }

    public VoucherType selectVoucherType() {
        System.out.println(ConsoleOutputMessage.TYPE_SELECT_MESSAGE);
        return VoucherType.findVoucherType(sc.nextLine());
    }

    public int insertDiscountValue() throws NumberFormatException {
        System.out.println(ConsoleOutputMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE);
        int inputValue = sc.nextInt();
        sc.nextLine();

        if (inputValue < 0) {
            logger.warn("0보다 작은 수는 들어올 수 없습니다.");
            throw new NumberFormatException();
        }

        return inputValue;
    }

    public Menu getMenu() {
        System.out.println(ConsoleOutputMessage.MENU_LIST_MESSAGE);
        Menu menu = Menu.findMenuType(sc.nextLine());

        while (menu == null) {
            System.out.println(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            menu = Menu.findMenuType(sc.nextLine());
        }

        return menu;
    }

    public VoucherMenu getVoucherMenu() {
        System.out.println(ConsoleOutputMessage.MENU_LIST_MESSAGE);
        VoucherMenu menu = VoucherMenu.findMenuType(sc.nextLine());

        while (menu == null) {
            System.out.println(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            menu = VoucherMenu.findMenuType(sc.nextLine());
        }

        return menu;
    }
}
