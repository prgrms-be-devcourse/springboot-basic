package org.devcourse.voucher.view;

import org.devcourse.voucher.error.ErrorType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputView implements Output{

    private static MenuProperties menu;

    public OutputView(MenuProperties menu) {
        this.menu = menu;
    }

    @Override
    public void mainMenu() {
        System.out.print(menu.getMain());
    }

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void printList(List<Object> list) {
        for (Object item : list) {
            System.out.println(list);
        }
    }

    @Override
    public void createMenu() {
        System.out.print(menu.getCreate());
    }

    @Override
    public void listMenu() {
        System.out.print(menu.getList());
    }

    @Override
    public void discountMenu() {
        System.out.print(menu.getDiscount());
    }

    @Override
    public void warn(ErrorType errorType) {
        System.out.println(errorType.message());
    }
}
