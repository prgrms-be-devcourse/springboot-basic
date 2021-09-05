package org.prgrms.kdt;

import org.prgrms.kdt.voucher.CreateVoucher;
import org.prgrms.kdt.voucher.HowMuchDiscount;
import org.prgrms.kdt.voucher.Validation;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineApplication {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        final Scanner scanner = new Scanner(System.in);

        final List<Voucher> mylist = new ArrayList<Voucher>();

        boolean programRunning = true;
        String VoucherType = "";

        do {
            System.out.println("=== Voucher Program ===");
            System.out.println("Type 'exit' for Exit.");
            System.out.println("Type 'create' to create a new voucher");
            System.out.println("Type 'list' to list all vouchers");

            final String commandInput = scanner.nextLine();

            switch (commandInput) {
                case "exit":
                    programRunning = false;
                    break;

                case "create":
                    boolean createTypeRunning = true;

                    System.out.println("=== Voucher Create ===");
                    System.out.println("Choose the type of voucher.");
                    System.out.println("- FixedAmountVoucher");
                    System.out.println("- PercentDiscountVoucher");

                    do {
                        VoucherType = scanner.nextLine();

                        switch (VoucherType) {
                            case "FixedAmountVoucher":
                                HowMuchDiscount.howMuchDiscountMessage(VoucherType); // 할인값을 입력해달라는 메세지
                                mylist.add(new CreateVoucher(
                                        Validation.fixedAmountDiscountValidation()) // 유효한 값인지 검사
                                        .createFixedAmountVoucher()); // voucher 생성
                                createTypeRunning = false;
                                break;

                            case "PercentDiscountVoucher":
                                HowMuchDiscount.howMuchDiscountMessage(VoucherType); // 할인값을 입력해달라는 메세지
                                mylist.add(new CreateVoucher(
                                        Validation.percentDiscountValidation()) // 유효한 값인지 검사
                                        .createPercentDiscountVoucher()); // voucher 생성
                                createTypeRunning = false;
                                break;

                            default:
                                HowMuchDiscount.howMuchDiscountMessage(VoucherType);
                                break;
                        }
                    } while (createTypeRunning);

                    break;

                case "list":
                    // list 목록 출력
                    for (int i = 0; i < mylist.size(); i++) {
                        System.out.println(mylist.get(i));
                    }
                    break;

                default:
                    System.out.println("=== Input type error ===");
                    System.out.println(MessageFormat.format("{0}은(는) 지원하지 않는 명령어입니다.", commandInput));
                    break;
            }
        } while (programRunning);
    }
}
