package org.prgms;

import org.prgms.service.VoucherService;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.PercentDiscountVoucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class CommandLineApplication
{
    private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        VoucherService service = context.getBean(VoucherService.class);
        while(true){
            printMessage();
            String inputText = input();
            if(inputText.equals("exit")){
                return;
            }
            else if(inputText.equals("create")){
                String num = inputVoucher();
                if(num.equals("1")){
                    service.createVoucher(new FixedAmountVoucher(10L, UUID.randomUUID()));
                }
                else{
                    service.createVoucher(new PercentDiscountVoucher(10L, UUID.randomUUID()));
                }
            }
            else if(inputText.equals("list")){
                service.listVoucher();
            }
        }
    }
    public static void printMessage(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
    }

    public static String input(){
        return scanner.nextLine();
    }

    public static String inputVoucher(){
        System.out.println("which one to create : 1. FixedAmountVoucher,  2. PercentDiscountVoucher");
        return scanner.nextLine();
    }
}
