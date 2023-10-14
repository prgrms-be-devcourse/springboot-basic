package org.prgrms.vouchermanager.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Input {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String selectMenu() throws IOException {
        String menu = br.readLine();
        return menu;
    }

    public String voucherInit() throws IOException{
        String mode = br.readLine(); // create or list or exit
        return mode;
    }

    public String createVoucher() throws IOException{
        String message = br.readLine(); // fixed or percent
        return message;
    }

    public String customerInit() throws IOException{
        String s = br.readLine(); // exit or list
        return s;
    }
}
