package org.prgrms.kdt.day2_work;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.UUID;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        var annotationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = annotationContext.getBean(VoucherService.class);

        var manual = new Manual();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;


        while (true){
            manual.startProgram();
            input = reader.readLine().toLowerCase().replace(" ", "");

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
                    input = reader.readLine();
                    if (Integer.parseInt(input) <= 100)
                        voucherService.createPercentVoucher(input);
                    else
                        System.out.println(input + " is wrong");
                }
                else
                    System.out.println(input + "is wrong command");
            }
            else if (input.equals("list")){
                if(voucherService.getVoucherList().isEmpty())
                    System.out.println("Voucher list is empty");
                else{
                    Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
                    for (UUID id : voucherList.keySet())
                    {
                        System.out.println(voucherList.get(id).showInfo());
                    }
                }
            }
            else{
                System.out.println(input + " is wrong command");
            }

            System.out.println();
        }

        annotationContext.close();
    }
}
