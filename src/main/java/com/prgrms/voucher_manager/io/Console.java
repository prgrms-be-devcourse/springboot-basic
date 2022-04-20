package com.prgrms.voucher_manager.io;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.StringTokenizer;

@Component
@Primary
public class Console implements Input, Output {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringTokenizer st;


    @Override
    public String input(String s) throws IOException {
        bw.write(s);
        bw.flush();
        return br.readLine();
    }

    @Override
    public String selectOption() throws IOException {
        bw.write("Select : ");
        bw.flush();
        st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    @Override
    public void wrongInput() throws IOException {
        printMessage(Message.WRONG_INPUT.getMessage());
    }

    @Override
    public void consoleMenu() throws IOException {
        printMessage(Message.MAIN_MENU.getMessage());
    }

    @Override
    public void createVoucher() throws IOException {
        printMessage(Message.CREATE_VOUCHER.getMessage());
    }

    @Override
    public void exitProgram() throws IOException {
        printMessage(Message.EXIT_PROGRAM.getMessage());
    }

    @Override
    public void emptyVoucherRepository() throws IOException {
        printMessage(Message.EMPTY_REPOSITORY.getMessage());
    }

    @Override
    public void emptyBlackList() throws IOException {
        printMessage(Message.EMPTY_BLACKLIST.getMessage());
    }


    private void printMessage (String message) throws IOException {
        bw.write(message);
        bw.newLine();
        bw.flush();
    }

}
