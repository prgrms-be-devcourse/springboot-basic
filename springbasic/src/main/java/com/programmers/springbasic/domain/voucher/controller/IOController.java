package com.programmers.springbasic.domain.voucher.controller;

import com.programmers.springbasic.domain.voucher.model.VoucherMenu;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.util.List;

@Controller
public class IOController {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public void showMenu() throws IOException {
        bw.write(VoucherMenu.MAIN_MENU_MESSAGE + "\n");
        bw.write(VoucherMenu.EXIT_MENU_MESSAGE + "\n");
        bw.write(VoucherMenu.CREATE_MENU_MESSAGE + "\n");
        bw.write(VoucherMenu.LIST_MENU_MESSAGE + "\n");
        bw.flush();
    }

    public String getInput() throws IOException {
        String input = br.readLine();

        return input;
    }

    public void printSingleOutput(String output) throws IOException {
        bw.write(output + "\n");
        bw.flush();
    }

    public void printListOutput(List<String> outputs) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (String output : outputs) {
            sb.append(output + "\n");
        }

        bw.write(sb.toString());
        bw.flush();
    }

    public void closeIOResource() throws IOException {
        br.close();
        bw.close();
    }
}
