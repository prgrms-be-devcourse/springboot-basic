package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        var annotationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = annotationContext.getBean(VoucherService.class);

        var manual = new Manual();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        manual.startProgram();
        while (true){
            input = reader.readLine().toLowerCase();

            if (input.equals("exit")){
                manual.exit();
                break;
            }
            else if (input.equals("create")){
                manual.chooseType();
                input = reader.readLine();
                if(input.equals("1")) {
                    manual.inputFixAmount();
                    voucherService.createFixVoucher(reader.readLine());
                }
                else if(input.equals("2")) {
                    manual.inputPercent();
                    voucherService.createPercentVoucher(reader.readLine());
                }
                else
                    System.out.println(input + "is wrong command");
            }
            else if (input.equals("list")){
                voucherService.getVoucherList().stream().forEach(s -> System.out.println(s.toString()));
            }
            else{
                System.out.println(input + "is wrong command");
            }

        }
    }
}
