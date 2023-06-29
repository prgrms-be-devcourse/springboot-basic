package com.devcourse.springbootbasic.application.template;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.dto.ListMenu;
import com.devcourse.springbootbasic.application.dto.Menu;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.io.InputConsole;
import com.devcourse.springbootbasic.application.io.OutputConsole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineTemplate {

    private final InputConsole input;
    private final OutputConsole output;
    private final CreateMenuTemplate createMenuTemplate;
    private final ListMenuTemplate listMenuTemplate;

    public CommandLineTemplate(
            InputConsole input, OutputConsole output,
            CreateMenuTemplate createMenuTemplate, ListMenuTemplate listMenuTemplate
    ) {
        this.input = input;
        this.output = output;
        this.createMenuTemplate = createMenuTemplate;
        this.listMenuTemplate = listMenuTemplate;
    }

    public void errorTask(Exception e) {
        output.printError(e);
    }

    public boolean isExitMenuBranch() {
        return switch (menuTask()) {
            case EXIT -> {
                endGame();
                yield true;
            }
            case CREATE -> {
                createTask();
                yield false;
            }
            case LIST -> {
                listTask();
                yield false;
            }
        };
    }

    private Menu menuTask() {
        output.showMenu();
        return input.readMenu();
    }

    private void endGame() {
        output.endPlatform();
    }

    private void listTask() {
        ListMenu listMenu = getUserListMenu();
        List<String> list = listMenuTemplate.listTask(listMenu);
        switch (listMenu) {
            case VOUCHER_LIST -> {
                output.printList(
                        Message.LIST_VOUCHERS_PROMPT.getMessageText(),
                        list
                );
            }
            case BLACK_CUSTOMER_LIST -> {
                output.printList(
                        Message.BLACK_CUSTOMER_PROMPT.getMessageText(),
                        list
                );
            }
        }
    }

    private ListMenu getUserListMenu() {
        output.showListMenu();
        return input.readListMenu();
    }

    private void createTask() {
        VoucherDto voucherDto = createVoucherDto();
        output.printVoucher(
                createMenuTemplate.createTask(voucherDto)
        );
    }

    private VoucherDto createVoucherDto() {
        output.showVoucherType();
        var voucherType = input.readVoucherType();
        return new VoucherDto(
                voucherType,
                input.readDiscountValue(voucherType)
        );
    }
}
