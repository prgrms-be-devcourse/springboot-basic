package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.io.Input;
import com.prgrms.springbootbasic.io.Output;
import jline.Terminal;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;


public class Console<T extends TextTerminal>  implements Input, Output {
    private TextIO textIO;
    private T terminal;

    @Override
    public String readCommand(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public String readString(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public void println(String message) {
        terminal.println(message);
    }

    public void run(){
        consoleMenu();
        while (true){
            String inputCommand = readCommand("명령어를 입력해주세요.(exit, create, list)");
            switch (inputCommand) {
                case "exit":
                    println("프로그램을 종료합니다.");
                    return;
                case "create":
                    create();
                    break;
                case "list":
                    list();
                    break;
                default:
                    println("허용된 명령어가 아닙니다. exit(종료), create(바우처 생성), list(바우처 목록)중 하나를 선택하세요. ");
                    break;
            }
        }
    }

    private void list() {

    }

    private void create() {

    }

    public void consoleMenu(){
        terminal.println("=== Voucher Program ===");
        terminal.println("Type exit to exit the program.");
        terminal.println("Type create to create a new voucher.");
        terminal.println("Type list to list all vouchers.");
    }

}
