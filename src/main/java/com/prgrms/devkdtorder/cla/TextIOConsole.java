package com.prgrms.devkdtorder.cla;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.util.List;

public class TextIOConsole implements Input, Output {

    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String getCommand() {
        return textIO.newStringInputReader()
                .read("명령어 입력 : ");
    }

    @Override
    public String getVoucherType() {
        return textIO.newStringInputReader()
                .read("Voucher 타입을 선택해주세요 (숫자or이름) :");
    }

    @Override
    public String getVoucherId() {
        return textIO.newStringInputReader()
                .read("Voucher Id :");
    }

    @Override
    public long getVoucherValue() {
        return textIO.newLongInputReader()
                .read("Voucher Value :");
    }


    @Override
    public void printAppStart() {
        String startMsg = "=== Voucher Program ===\n" +
                "Type **exit** to exit the program.\n" +
                "Type **create** to create a new voucher.\n" +
                "Type **list** to list all vouchers.";
        textIO.getTextTerminal().println(startMsg);
    }

    @Override
    public void printCommandError() {
        textIO.getTextTerminal().println("잘못된 명령어 입니다!");
    }

    @Override
    public void print(String prompt) {
        textIO.getTextTerminal().println(prompt);
    }

    @Override
    public void print(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((i+1) + ". "+list.get(i)+"\n");
        }
        textIO.getTextTerminal().println(sb.toString());
    }
}
