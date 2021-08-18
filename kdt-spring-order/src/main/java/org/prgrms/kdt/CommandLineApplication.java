package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {

    private final String EXIT_COMMAND = "exit";
    private final String CREATE_COMMAND = "create";
    private final String LIST_COMMAND = "liest";

    private ApplicationContext context;
    private VoucherService voucherService;
    private Scanner scanner;

    public CommandLineApplication(){
        context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = context.getBean(VoucherService.class);
        scanner = new Scanner(System.in);
    }

    public void start(){
        printStartMessage();
        String command = scanner.next();

        while(!command.equals("exit")){
            if(command.equals("create")){
                executeCreateCommand();
            }
            else if(command.equals("list")){
                executeListCommand();
            }
            else{
                System.out.println("유효하지 않은 커맨드입니다.");
            }

            System.out.print("커맨드를 입력하세요(exit, create, list) : ");
            command = scanner.next();
        }

        System.out.println("프로그램을 종료합니다.");
    }

    private void printStartMessage(){
        System.out.println("======== 바우처 프로그램 ========");
        System.out.println("  exit 커맨드 : 프로그램 종료");
        System.out.println("create 커맨드 : 바우처 생성");
        System.out.println("  list 커맨드 : 모든 바우처 조회");
        System.out.println("=============================");
        System.out.print("커맨드를 입력하세요(exit, create, list) : ");
    }

    private void executeCreateCommand(){
        System.out.print("바우처 타입 번호를 입력하세요. (1-FixedAmount, 2-PrecentDiscount) : ");
        int type = scanner.nextInt();

        if(type == 1){ //FixedAmountVoucher
            System.out.print("할인 금액을 입력하세요. :  ");
            long discountAmount = scanner.nextLong();

            voucherService.addVoucher(
                    voucherService.createVoucher(VoucherType.FIXED_AMOUNT, UUID.randomUUID(), discountAmount));
        }
        else if(type == 2) //PercentDiscountVoucher
        {
            System.out.print("할인률을 입력하세요 : ");
            long discountPercent = scanner.nextLong();

            if((discountPercent < 0) || (discountPercent > 100)){
                System.out.println("유효하지 않은 할인률입니다. 0~100 사이의 값을 입력하세요.");
                return;
            }

            voucherService.addVoucher(
                    voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT, UUID.randomUUID(), discountPercent));
        }
        else
        {
            System.out.println("유효하지 않은 바우처 타입입니다.");
        }
    }

    private void executeListCommand(){
        List<Voucher> vouchers = voucherService.getAllVouchers();

        System.out.println(MessageFormat.format("[총 {0}개의 바우처가 있습니다.]", vouchers.size()));

        for(Voucher voucher : vouchers){
            System.out.println(voucher);
        }
    }

    public static void main(String[] args){
        CommandLineApplication commandLineApplication = new CommandLineApplication();
        commandLineApplication.start();
    }
}
