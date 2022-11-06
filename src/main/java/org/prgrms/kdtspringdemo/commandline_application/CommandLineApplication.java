package org.prgrms.kdtspringdemo.commandline_application;

import org.prgrms.kdtspringdemo.io.Console;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.prgrms.kdtspringdemo.voucher.VoucherType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements ApplicationRunner {
    private boolean isRunning = true;
    private final Console console;
    private VoucherService voucherService;
    public CommandLineApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run(ApplicationArguments args) {
        while(isRunning){
            //output 책임
            console.showMenu();
            String myString = console.getInputWithPrompt("");
            executeMenuByInput(myString);
        }
    }

    //분기하는 책임
    private void executeMenuByInput(String myString) {
        switch (CommandType.getTypeByName(myString)) {
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
                showError();
            }
        }
    }
    private void exitProgram(){
        System.out.println("종료하기");
        this.isRunning = false;

    }
    private void createVoucher(){
        System.out.println("create 로직");
        VoucherType voucherType =console.selectVoucherTypeMenu();
        Long value = console.getVoucherValue();

    }
    private void showVoucherList(){
        System.out.println("List 로직");

    }
    private void showError(){
        System.out.println("에러 처리 로직");
        console.showError(new Exception("잘못된 입력 입니다."));
    }

}
