package org.programmers.applicationcontext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {

    public static void main(String[] args) throws IOException {
        List<Voucher> voucherList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        var commandLineContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Scanner sc = new Scanner(System.in);
        loop:
        while (true){
            System.out.println("====== Voucher Program ======\n" +
                    "Type exit to exit the program.\n" +
                    "Type create to create a new voucher.\n" +
                    "Type list to list all vouchers.");

            String line = bufferedReader.readLine();

            switch (line) {
                case "exit":
                    break loop;
                case "create":
                    createVoucher(voucherList, commandLineContext,sc);
                    break;
                case "list":
                    listVoucher(voucherList);
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요");
                    break;
            }

        }

        System.out.println("프로그램을 종료합니다");
        sc.close();
        bufferedReader.close();
    }

    private static void listVoucher(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println("\n"+"Voucher 종류: "+voucher.getClass().getName());
            System.out.println("바우처 아이디 = "+voucher.getVoucherId() +"\n"
                    + "바우처 할인가격(비율) = " + voucher.getVoucherVolume());
        }
        System.out.println("\n");
    }

    private static void createVoucher(List<Voucher> voucherList,
                                      AnnotationConfigApplicationContext commandLineContext,
                                      Scanner sc) throws IOException {
        var voucherId = UUID.randomUUID();
        var voucherService = commandLineContext.getBean(VoucherService.class);

        while (true){
            System.out.println("1. FixedAmountVoucher\n" +
                    "2. PercentDiscountVoucher\n" +
                    "생성하고 싶은 Voucher의 종류를 숫자로 쓰시오\n" +
                    "ex) 1");
            String voucherType = sc.next();

            if(voucherType.equals("1")){

                System.out.println("FixedAmountVoucher에 부여할 할인값을 작성하시오");
                long amount = sc.nextLong();
                var fixedAmountVoucher = voucherService.createFixedAmountVoucher(voucherId, amount);
                voucherList.add(fixedAmountVoucher);
                break;

            }
            else if(voucherType.equals("2")){

                System.out.println("PercentDiscountVoucher에 부여할 할인값을 작성하시오");
                long percent = sc.nextLong();
                var percentDiscountVoucher = voucherService.createPercentDiscountVoucher(voucherId, percent);
                voucherList.add(percentDiscountVoucher);
                break;

            }
            else{
                System.out.println("숫자를 다시 입력하시오\n");
            }
        }

    }

}
