package prgms.spring_week1.io;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;
import prgms.spring_week1.menu.Menu;

import java.util.Scanner;

public class Input {
    private final Scanner sc = new Scanner(System.in);

    public String inputString() {
        return sc.nextLine();
    }

    public VoucherType selectVoucherType() throws NoSuchVoucherTypeException {
        return VoucherType.findVoucherType(this.inputString());
    }

    public Menu selectMenu() {
        return Menu.findMenuType(this.inputString());
    }

    public Integer insertDiscountValue() {
        return Integer.parseInt(this.inputString());
    }

}
