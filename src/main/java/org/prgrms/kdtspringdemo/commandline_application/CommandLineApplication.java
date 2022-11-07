package org.prgrms.kdtspringdemo.commandline_application;

import org.prgrms.kdtspringdemo.io.console.Console;
import org.prgrms.kdtspringdemo.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.prgrms.kdtspringdemo.voucher.model.VoucherType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements ApplicationRunner {
    private boolean isRunning = true;
    private final Console console;
    private final VoucherService voucherService;

    public CommandLineApplication(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) {
        while (isRunning) {
            //output 책임
            console.showMenu();
            String myString = console.getInputWithPrompt("");
            executeMenuByInput(myString);
        }
    }

    //분기하는 책임
    private CommandType executeMenuByInput(String myString) {
        CommandType commandType = CommandType.getTypeByName(myString);
        switch (commandType) {
            case EXIT -> {
                //종료하는 책임
                exitProgram();
            }
            case CREATE -> {
                //생성하는 책임
                createVoucher();
            }
            case LIST -> {
                //list를 보여주는 책임
                showVoucherList();
            }
            case ERROR -> {
                //에러를 처리하는 책임
                showWrongCommandError();
            }
        }
        return commandType;
    }

    private void exitProgram() {
        System.out.println("종료하기");
        this.isRunning = false;

    }

    private Voucher createVoucher() {
        while (true) {
            try {
                System.out.println("create 로직");
                VoucherType voucherType = console.selectVoucherTypeMenu();
                Long value = console.getVoucherValue();
                return voucherService.createVoucher(voucherType, value);
            }catch (Exception e){
                console.showError(e);
                continue;
            }
        }
    }

    private void showVoucherList() {
        System.out.println("List 로직");
        List<Voucher> voucherList = voucherService.getAllVoucherList();
        console.showList(voucherList);
    }

    private void showWrongCommandError() {
        System.out.println("에러 처리 로직");
        console.showError(new Exception("잘못된 입력 입니다."));
    }

}
