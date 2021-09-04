package org.prgrms.kdt;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        final Scanner scanner = new Scanner(System.in);

        final List<Voucher> mylist = new ArrayList<Voucher>();

        boolean programRunning = true;

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
                        final String voucherType = scanner.nextLine();

                        switch (voucherType) {
                            case "FixedAmountVoucher":
                                System.out.println("할인 가격을 얼마로 설정하시겠습니까?");
                                final String amount = scanner.nextLine();
                                mylist.add(new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(amount)));

                                System.out.println(voucherType + "가 생성되었습니다.");
                                createTypeRunning = false;
                                break;

                            case "PercentDiscountVoucher":
                                System.out.println("할인율을 몇 퍼센트로 설정하시겠습니까?");
                                String discountAmount = null;

                                boolean discountAmountCheck = true;
                                while (discountAmountCheck) {
                                    final String percent = scanner.nextLine();

                                    if (Long.parseLong(percent) > 100 || Long.parseLong(percent) <= 0) {
                                        System.out.println("할인율은 0 초과, 100 이하로 설정해주십시오.");
                                    } else discountAmountCheck = false;
                                    
                                    discountAmount = percent;
                                }

                                mylist.add(new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(discountAmount)));
                                System.out.println(voucherType + "가 생성되었습니다.");
                                createTypeRunning = false;
                                break;

                            default:
                                System.out.println("=== Input type error ===");
                                System.out.println(voucherType + " does not exist in Voucher Program.");
                                System.out.println("'FixedAmountVoucher', 'PercentDiscountVoucher'");
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
                    System.out.println(MessageFormat.format("{0} does not exist in Voucher Program.", commandInput));
                    System.out.println("'exit', 'create', 'list'");
                    break;
            }
        } while (programRunning);
    }
}
