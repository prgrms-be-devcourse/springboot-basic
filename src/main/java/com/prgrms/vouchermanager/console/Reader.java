package com.prgrms.vouchermanager.console;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class Reader {

    Scanner sc = new Scanner(System.in);

    String blackListFilePath;
    String voucherFilePath;

    public BufferedReader bf;
    public BufferedReader vf;

    public Reader(@Value("${csv.blacklist}") String blackListFilePath, @Value("${csv.voucher}") String voucherFilePath) throws IOException {
        this.blackListFilePath = blackListFilePath;
        this.voucherFilePath = voucherFilePath;
        bf = new BufferedReader(new FileReader(blackListFilePath));;
        vf = new BufferedReader(new FileReader(voucherFilePath));
    }
}
