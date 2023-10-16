package com.prgrms.vouchermanager.console;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class Reader {

    Scanner sc = new Scanner(System.in);

//    @Value("${csv.blacklist}")
//    String blackListFilepath;

    public BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/customer_blacklist.csv"));;
    public BufferedReader vf = new BufferedReader(new FileReader("src/main/resources/voucher_list.csv"));;

    public Reader() throws IOException {
    }
}
