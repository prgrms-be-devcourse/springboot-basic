package com.mountain.voucherApp.io;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.enums.Menu;
import com.mountain.voucherApp.voucher.VoucherEntity;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.mountain.voucherApp.constants.Message.*;
import static com.mountain.voucherApp.utils.MenuUtil.getMenuMap;

@Component
public class OutputConsole implements Output {

    public static TextTerminal<?> textTerminal = TextIoFactory.getTextIO().getTextTerminal();

    @Override
    public void printManual() {
        textTerminal.println(MANUAL_TITLE);
        Map<String, Menu> menuMap = getMenuMap();
        for (String key : menuMap.keySet()) {
            textTerminal.printf(MessageFormat.format("{0} ", TYPE));
            textTerminal.printf(key);
            Menu menu = menuMap.get(key);
            textTerminal.printf(MessageFormat.format(" {0}{1}", menu.getDescription(), System.lineSeparator()));
        }
    }

    @Override
    public void printWrongInput() {
        textTerminal.println(WRONG_INPUT);
    }

    @Override
    public void choiceDiscountPolicy() {
        Arrays.stream(DiscountPolicy.values())
                .forEach(
                        (p) -> textTerminal.print(MessageFormat.format(
                                "{0}. {1}{2}",
                                p.getPolicyId(),
                                p.getDescription(),
                                System.lineSeparator())
                        )
                );
    }

    @Override
    public void printAmount() {
        textTerminal.println(PLEASE_AMOUNT);
    }

    @Override
    public void printAllList(List<VoucherEntity> repository) {
        repository.stream()
                .forEach((voucher) -> textTerminal.print(voucher.toString()));
    }

    @Override
    public void printException(Exception e) {
        textTerminal.println(e.getMessage());
    }

    @Override
    public void close() {
        textTerminal.abort();
    }

}
