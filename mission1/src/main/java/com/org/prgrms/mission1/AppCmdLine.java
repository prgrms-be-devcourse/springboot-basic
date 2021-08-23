package com.org.prgrms.mission1;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.util.List;

public class AppCmdLine {
    public static void main(String[] args) throws IOException, CsvException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);


        while (true) {
            startMessage();
            String cmd = br.readLine();

            switch(cmd){
                case "exit":   //Exit
                    System.out.println("Exit the program.");
                    System.exit(0);

                case "create":  // Createe
                    System.out.println("Choose a Voucher type\n" +
                            "1 : Fixed\n" +
                            "2 : Percent");

                    String type = br.readLine();
                    Voucher voucher;
                    if(type.equals("Fixed") || type.equals("1")) {
                        voucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT);  //FixedAmount 객체 생성->hashmap에 저장
                        voucherService.save(voucher); //csv 파일로 저장
                    }

                    else if(type.equals("Percent") || type.equals("2")) {
                        voucher=voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT); //PercentDiscount 객체 생성 -> hashmap에 저장
                        voucherService.save(voucher);  //csv 파일로 저장
                    }
                    else
                        System.out.println("The input is invalid."); // 다른 값이 입력된 경우
                    break;

                case "list":   //List
                    voucherService.getAllVoucher().forEach(System.out::println);   // List  출력
                    break;

                default:
                    System.out.println("The input is invalid.");
            }
        }
    }
    private static void startMessage() throws FileNotFoundException {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n" +
                ">>> ");
    }

}
