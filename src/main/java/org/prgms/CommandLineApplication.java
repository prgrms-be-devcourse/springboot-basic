package org.prgms;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var io = applicationContext.getBean(Io.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        Scanner sc = new Scanner(System.in);

        io.help();

        while(true){

            String input = sc.next();

            switch(input){
                case "exit":
                    io.exit();
                    System.exit(0);

                case "create":
                    io.create();
                    String voucherType = sc.next();
                    System.out.println("Enter voucher amount : ");
                    int amount = sc.nextInt();
                    voucherService.createVoucher(voucherType, UUID.randomUUID() , amount);
                    break;

                case "list":
                    List<Voucher> getVouchers = voucherService.getVouchers();
                    getVouchers.forEach(System.out::println);
                    break;


            }


        }
    }

}
