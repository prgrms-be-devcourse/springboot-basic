package org.devcourse.voucher.core.view.console;

import org.devcourse.voucher.core.configuration.MenuProperties;
import org.devcourse.voucher.core.exception.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputView implements Output{

    private static MenuProperties menu;
    private final Logger logger = LoggerFactory.getLogger(OutputView.class);

    public OutputView(MenuProperties menu) {
        this.menu = menu;
    }

    @Override
    public void mainMenu() {
        System.out.print(menu.getMain() + " ");
    }

    @Override
    public void info(String msg) {
        System.out.println(msg);
        logger.info(msg);
    }

    @Override
    public void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

    @Override
    public void createMenu() {
        System.out.print(menu.getCreate() + " ");
    }

    @Override
    public void listMenu() {
        System.out.print(menu.getList() + " ");
    }

    @Override
    public void discountMenu() {
        System.out.print(menu.getDiscount());
    }

    @Override
    public void voucherMenu() {
        System.out.print(menu.getVoucher() + " ");
    }

    @Override
    public void nameMenu() {
        System.out.print(menu.getName() + " ");
    }

    @Override
    public void emailMenu() {
        System.out.print(menu.getEmail() + " ");
    }

    @Override
    public void error(ErrorType errorType) {
        String errorMessage = errorType.message();

        System.out.println(errorMessage);
        logger.error(errorMessage);
    }
}
