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
    
    // 생성자를 통해 프로그램을 실행시킵니다.
    public CommandLineApplication(VoucherService voucherService) throws IOException {
        this.voucherService = voucherService;
        runApp();
    }
    
    // 내부 로직
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
    
    // VoucherService의insertVoucher를 호출하는 메소드
    private void createVoucher(String command){
        if(command.equals("1")){
            voucherService.insertVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10000L));
        } else {
            voucherService.insertVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10L));
        }

    }
    // VoucherService를 통해 Voucher리스트를 문자열로 받아오는 메소드
    private void printVouchers(){
        System.out.println(voucherService.showVouchers());
    }
    
    // 아래의 메소드들은 출력용 메소드
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
