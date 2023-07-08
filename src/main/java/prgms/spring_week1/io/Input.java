package prgms.spring_week1.io;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;
import prgms.spring_week1.menu.Menu;

import java.util.Scanner;

public class Input {
    private final Scanner sc = new Scanner(System.in);

    public VoucherType selectVoucherType() throws NoSuchVoucherTypeException {
        return VoucherType.findVoucherType(sc.nextLine());
    }

    public Menu selectMenu() {
        return Menu.findMenuType(sc.nextLine());
    }

    public int insertDiscountValue() {
        return sc.nextInt();
    }

}
