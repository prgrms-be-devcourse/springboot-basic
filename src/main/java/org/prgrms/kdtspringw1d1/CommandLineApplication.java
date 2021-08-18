package org.prgrms.kdtspringw1d1;

import org.prgrms.kdtspringw1d1.config.AppConfiguration;
import org.prgrms.kdtspringw1d1.voucher.Voucher;
import org.prgrms.kdtspringw1d1.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineApplication {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static final String noticeText = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.";

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        String command;
        String voucherType;

        System.out.println(noticeText);

        while(true){
            command = reader.readLine();
            if(command.equals("exit")){
                System.out.println("BYE.");
                return;
            }
            else if(command.equals("create")){
                System.out.println("Type fixed or percent for the type of voucher you want to create.");
                voucherType = reader.readLine();
                while(!voucherType.equals("fixed") && !voucherType.equals("percent")){
                    System.out.println("Type either fixed or percent.");
                    voucherType = reader.readLine();
                }
                Voucher createdVoucher = null;
                if(voucherType.equals("fixed")){
                    createdVoucher = voucherService.createVoucher(VoucherType.Fixed);
                }
                else if(voucherType.equals("percent")){
                    createdVoucher = voucherService.createVoucher(VoucherType.Percent);
                }
                System.out.println("Success creating Voucher : "+createdVoucher.getVoucherId());

            }
            else if(command.equals("list")){
                List<Voucher> vouchers = voucherService.getVoucherAll();
                for (int i = 0; i < vouchers.size(); i++) {
                    System.out.println(i+1+" 번 Voucher : "+vouchers.get(i).getVoucherId());
                };
            }
        }

    }


}
