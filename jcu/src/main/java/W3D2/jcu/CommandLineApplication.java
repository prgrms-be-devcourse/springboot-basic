package W3D2.jcu;

import W3D2.jcu.voucher.FixedAmountVoucher;
import W3D2.jcu.voucher.PercentDiscountVoucher;
import W3D2.jcu.voucher.VoucherService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class CommandLineApplication {
    private final VoucherService voucherService;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public CommandLineApplication(VoucherService voucherService) throws IOException {
        this.voucherService = voucherService;
        runApp();
    }

    private void runApp() throws IOException {
        String command;
        while(true){
            printInitMessage();
            command = br.readLine();

            if(command.equals("exit")) break;
            else if(command.equals("create")){
                printCreateMessage();
                command = br.readLine();
                if(command.equals("1") || command.equals("2")){
                    createVoucher(command);
                } else {
                    printErrorMessage();
                }
            }
            else if(command.equals("list")){
                printVouchers();
            }
            else {
                printErrorMessage();
            }
        }

    }

    private void createVoucher(String command){
        if(command.equals("1")){
            voucherService.insertVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10000L));
        } else {
            voucherService.insertVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10L));
        }

    }
    private void printVouchers(){
        System.out.println(voucherService.showVouchers());
    }
    private void printInitMessage(){
        System.out.println("\n===== Voucher Program =====\n"
            + "Type exit to exit the program.\n"
            + "Type create to create a new voucher.\n"
            + "Type list to list all vouchers.");
        System.out.print("\n>> ");
    }
    private void printErrorMessage(){
        System.out.println("Wrong Command.");
    }
    private void printCreateMessage(){
        System.out.println("==== Choose one ====");
        System.out.println("1.FixedAmountVoucher 2.PercentDiscountVoucher");
        System.out.print("\n>> ");
    }

}
