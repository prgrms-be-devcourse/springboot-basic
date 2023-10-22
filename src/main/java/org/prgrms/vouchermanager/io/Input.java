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
        return mode.toLowerCase();
    }

    public String createVoucher() throws IOException{
        String message = br.readLine(); // fixed or percent
        return message.toLowerCase();
    }

    public String customerInit() throws IOException{
        String s = br.readLine().toLowerCase(); // exit or list
        return s;
    }

    public String inputCustomerName() throws IOException{
        String name = br.readLine();
        return name;
    }

    public String inputCustomerEmail() throws IOException{
        String email = br.readLine();
        return email;
    }
    public String inputCustomerisBlack() throws IOException{
        String isBlack = br.readLine();
        return isBlack;
    }

    // ---------Wallet 메뉴 관련




}
