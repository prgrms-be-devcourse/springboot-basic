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
        //return br.readLine();
    }

    @Override
    public void wrongInput() throws IOException {
        bw.write(Message.WRONG_INPUT.getMessage());
        bw.newLine();
        bw.flush();
    }

    @Override
    public void consoleMenu() throws IOException {
        bw.write(Message.CONSOLE_INPUT.getMessage());
        bw.newLine();
        bw.flush();
    }

    @Override
    public void selectVoucher() throws IOException {
        bw.write(Message.CREATE_VOUCHER.getMessage());
        bw.newLine();
        bw.flush();
    }

    @Override
    public void exitProgram() throws IOException {
        bw.write(Message.EXIT_PROGRAM.getMessage());
        bw.newLine();
        bw.flush();
    }

    @Override
    public void emptyVoucherRepository() throws IOException {
        bw.write(Message.EMPTY_REPOSITORY.getMessage());
        bw.newLine();
        bw.flush();
    }


}
