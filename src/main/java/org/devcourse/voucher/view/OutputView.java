package org.devcourse.voucher.view;

import org.devcourse.voucher.configuration.MenuProperties;
import org.devcourse.voucher.error.ErrorType;
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
    public void warn(ErrorType errorType) {
        System.out.println(errorType.message());
        logger.warn(errorType.message());
    }
}
