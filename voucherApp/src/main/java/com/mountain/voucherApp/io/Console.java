package com.mountain.voucherApp.io;

import com.mountain.voucherApp.customer.Customer;
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

import static com.mountain.voucherApp.constants.Color.*;
import static com.mountain.voucherApp.constants.CommonCharacter.INPUT_PROMPT;
import static com.mountain.voucherApp.constants.Message.*;
import static com.mountain.voucherApp.utils.MenuUtil.getMenuMap;

@Component
public class Console implements Input, Output {

    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    private void changeColor(String color) {
        textTerminal.getProperties().setPromptColor(color);
    }

    @Override
    public String input() {
        changeColor(INPUT_COLOR);
        String command = textIO.newStringInputReader()
                .read(INPUT_PROMPT);
        return command;
    }

    @Override
    public void close() {
        textTerminal.abort();
    }


    @Override
    public void printMessage(String msg) {
        textTerminal.println(msg);
    }

    @Override
    public void printManual() {
        changeColor(MANUAL_COLOR);
        textTerminal.println(MANUAL_TITLE);
        Map<Integer, Menu> menuMap = getMenuMap();
        for (Integer key : menuMap.keySet()) {
            Menu menu = menuMap.get(key);
            textTerminal.printf(MessageFormat.format("{0}.{1} ",menu.ordinal(), TYPE));
            changeColor(MENU_COLOR);
            textTerminal.printf(menu.getValue());
            changeColor(MANUAL_COLOR);
            textTerminal.printf(MessageFormat.format(" {0}{1}", menu.getDescription(), System.lineSeparator()));
        }
    }

    @Override
    public void choiceDiscountPolicy() {
        changeColor(SELECT_COLOR);
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

    private boolean validateList(List dataList) {
        if (dataList == null || dataList.size() == 0) {
            textTerminal.println(EMPTY_DATA);
            return false;
        }
        return true;
    }

    @Override
    public void printVoucherList(List<VoucherEntity> voucherEntityList) {
        if (validateList(voucherEntityList)) {
            for (int i = 0; i < voucherEntityList.size(); i++) {
                VoucherEntity voucherEntity = voucherEntityList.get(i);
                textTerminal.println(MessageFormat.format("{0}. 할인유형:{1}, 할인금액(비율): {2}",
                        i,
                        voucherEntity.getDiscountPolicyId(),
                        voucherEntity.getDiscountAmount()));
            }
        }
    }

    @Override
    public void printCustomerList(List<Customer> customerList) {
        if (validateList(customerList)) {
            for (int i = 0; i < customerList.size(); i++) {
                Customer customer = customerList.get(i);
                textTerminal.println(MessageFormat.format("{0}. {1}",
                        i,
                        customer.getEmail()));
            }
        }
    }

    @Override
    public void printCustomerVoucherInfo(List<Customer> customerList) {
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            textTerminal.println(MessageFormat.format("{0}. customerEmail : {1}, voucherId : {2}",
                    i,
                    customer.getEmail(),
                    customer.getVoucherId()));
        }
    }

    @Override
    public void printException(Exception e) {
        changeColor(ERR_COLOR);
        textTerminal.println(MessageFormat.format("[{0}]: {1}", e.getClass().getCanonicalName(), e.getMessage()));
    }

}
