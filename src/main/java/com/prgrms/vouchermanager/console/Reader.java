package com.prgrms.vouchermanager.console;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class Reader {

    Scanner sc = new Scanner(System.in);

//    @Value("${csv.path}")
//    private String path;

    File blackListFile = new File("src\\main\\resources\\customer_blacklist.csv");
    public BufferedReader bf = new BufferedReader(new FileReader(blackListFile));

    File voucherFile = new File("src/main/resources/voucher_list.csv");
    public BufferedReader vf = new BufferedReader(new FileReader(voucherFile));

    public Reader() throws IOException {
    }
}
