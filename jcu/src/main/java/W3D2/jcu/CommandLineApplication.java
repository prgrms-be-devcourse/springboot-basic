package W3D2.jcu;

import W3D2.jcu.voucher.FixedAmountVoucher;
import W3D2.jcu.voucher.PercentDiscountVoucher;
import W3D2.jcu.voucher.VoucherService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineApplication {
    private final VoucherService voucherService;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Quest : 생성자 예외처리
    //  - @RequiredArgsConstructor로 생성자를 생략했는데 생성자에서의 예외처리는 어떻게 되는걸까?

    @PostConstruct
    public void postConstruct() throws IOException {
        // Quest : 초기화
        //  - 초기화시 가장 좋은 방법은 ?
        //  - (postConstruct) or (initMethod) or (afterPropertiesSet) ?
        voucherService.loadStorage();
        runApp();
    }

    private void runApp() throws IOException {
        String command;
        while(true) {
            printInitMessage();
            command = br.readLine();

            // Todo : enum
            if(command.equals("exit")) {
                voucherService.saveStorage();
                break;
            }
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
        br.close();
    }

    private void createVoucher(String command){
        if(command.equals("1")){
            // Todo : enum
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