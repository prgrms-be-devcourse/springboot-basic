package com.wonu606.Controller;

import com.wonu606.Controller.command.CommandResult;
import com.wonu606.Controller.command.VoucherCommand;
import com.wonu606.Controller.command.VoucherCreateCommand;
import com.wonu606.Controller.command.VoucherExitCommand;
import com.wonu606.Controller.command.VoucherListCommand;
import com.wonu606.vouchermanager.descriptionGenerator.CreationDescriptionGenerator;
import com.wonu606.vouchermanager.descriptionGenerator.ExitDescriptionGenerator;
import com.wonu606.vouchermanager.descriptionGenerator.MenuDescriptionGenerator;
import com.wonu606.vouchermanager.io.ConsoleInputView;
import com.wonu606.vouchermanager.io.ConsolePrinterView;
import com.wonu606.vouchermanager.service.VoucherService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoucherController {

    private final VoucherService voucherService;
    private final Map<String, VoucherCommand> commandMap;
    private final ConsoleInputView inputView;
    private final ConsolePrinterView printerView;

    public VoucherController(ConsoleInputView inputView, ConsolePrinterView printerView,
            VoucherService voucherService) {
        this.voucherService = voucherService;
        this.inputView = inputView;
        this.printerView = printerView;

        commandMap = new HashMap<>();
        initCommandMap(commandMap);
        initPrinterView(this.printerView);
    }

    public void run() {
        CommandResult result = new CommandResult();
        while (result.isContinuing()) {
            String menuSelection = getMenuSelection();

            result = executeCommand(menuSelection);
            reportResult(result);
        }
        close();
    }

    private String getMenuSelection() {
        printerView.printMenu();
        return inputView.inputString("menu");
    }

    private CommandResult executeCommand(String commandKey) {
        VoucherCommand command = commandMap.get(commandKey);
        if (command == null) {
            return createErrorResult("존재하지 않는 메뉴입니다.");
        }

        try {
            return command.execute();
        } catch (Exception e) {
            return createErrorResult(e.getMessage());
        }
    }

    private CommandResult createErrorResult(String exceptionMessage) {
        CommandResult result = new CommandResult();
        result.setExceptionMessage(exceptionMessage);
        return result;
    }

    private void reportResult(CommandResult result) {
        result.getExceptionMessage().ifPresent(printerView::printMessage);
    }

    private void close() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        inputView.close();
        printerView.close();
    }

    private void initCommandMap(Map<String, VoucherCommand> commandMap) {
        commandMap.put("create", new VoucherCreateCommand(inputView, printerView, voucherService));
        commandMap.put("list", new VoucherListCommand(printerView, voucherService));
        commandMap.put("exit", new VoucherExitCommand(printerView));
    }

    private void initPrinterView(ConsolePrinterView printerView) {
        printerView.setMenuDescription(
                new MenuDescriptionGenerator().generate(new ArrayList<>(commandMap.keySet()))
        );
        printerView.setCreationDescription(
                new CreationDescriptionGenerator().generate(voucherService.getVoucherTypes())
        );
        printerView.setExitDescription(
                new ExitDescriptionGenerator().generate()
        );
    }


}
