package com.org.prgrms.mission;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.model.ConsumerType;
import com.org.prgrms.mission.model.Customer;
import com.org.prgrms.mission.model.Voucher;
import com.org.prgrms.mission.model.VoucherType;
import com.org.prgrms.mission.repository.MemoryVoucherRepository;
import com.org.prgrms.mission.repository.VoucherRepository;
import com.org.prgrms.mission.service.CustomerService;
import com.org.prgrms.mission.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.text.MessageFormat;

public class AppCmdLine {
    public static void main(String[] args) throws IOException, CsvException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var enviroment = applicationContext.getEnvironment();
        enviroment.setActiveProfiles("local");
        applicationContext.refresh();


        var voucherService = applicationContext.getBean(VoucherService.class);
        var customerService = applicationContext.getBean(CustomerService.class);


        while (true) {
            startMessage();
            String cmd = br.readLine();

            switch(cmd){
                case "exit":   //Exit
                    System.out.println("Exit the program.");
                    System.exit(0);

                case "create":  // Createe
                    System.out.println("Choose one \n" +
                            "1 : CreateVoucher\n" +
                            "2 : CreateCustomer\n");
                    String cmd2 = br.readLine();
                    if(cmd2.equals("Create Voucher")||cmd2.equals("1")){
                        createVoucher(br,voucherService);
                    }
                    else if(cmd2.equals("Create Customer")||cmd2.equals("2")){
                        createCustomer(br,customerService);
                    }
                    break;

                case "list":   //List
                    System.out.println("Choose one \n" +
                            "1 : Show voucher.csv\n" +
                            "2 : Show customer.csv\n" +
                            "3 : Show blacklist.csv\n");
                    String cmd3 = br.readLine();
                    switch (cmd3) {
                        case "1" :
                            voucherService.getAllVoucher().forEach(System.out::println);   // List  출력
                            break;
                        case "2":
                            customerService.findAll("Customer").forEach(System.out::println);
                            break;
                        case "3" :
                            customerService.findAll("Blacklist").forEach(System.out::println);
                            break;
                    }

                    break;

                default:
                    System.out.println("The input is invalid.");
            }
        }
    }
    private static void startMessage() throws FileNotFoundException {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher or customer.\n" +
                "Type list to list all vouchers or customerList or BlackList.\n" +
                ">>> ");
    }

    private static void createVoucher(BufferedReader br, VoucherService voucherService) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
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

    }
    private static void createCustomer(BufferedReader bufferedReader, CustomerService customerService) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        System.out.println("insert name");
        String name = bufferedReader.readLine();
        System.out.println("insert age");
        int age = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Choose one\n" +
                "1 : Black Consumer\n" +
                "2 : Consumer");
        String cmd = bufferedReader.readLine();
        ConsumerType consumerType;
        if(cmd.equals("1")) consumerType = ConsumerType.BLACK_CONSUMER;
        else consumerType = ConsumerType.WHILE_CONSUMER;

        Customer customer = customerService.createCustomer(name, age, consumerType);
        customerService.saveCustomer(customer);
    }

}
