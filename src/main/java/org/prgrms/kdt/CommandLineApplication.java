package org.prgrms.kdt;

import org.prgrms.kdt.voucher.CreateVoucher;
import org.prgrms.kdt.voucher.DiscountValidation;
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
                                CreateVoucher.howMuchDiscountMessage(VoucherType);
                                long amount = 0;

                                boolean discountAmountCheck1 = true;
                                while (discountAmountCheck1) {
                                    amount = Long.parseLong(scanner.nextLine());
                                    discountAmountCheck1 = new DiscountValidation(amount).amountValidation();
                                }

                                mylist.add(new CreateVoucher(amount).createFixedAmountVoucher());
                                createTypeRunning = false;
                                break;

                            case "PercentDiscountVoucher":
                                CreateVoucher.howMuchDiscountMessage(VoucherType);
                                float discountPercent = 0;

                                boolean discountAmountCheck2 = true;
                                while (discountAmountCheck2) {
                                    discountPercent = Float.parseFloat(scanner.nextLine());
                                    discountAmountCheck2 = new DiscountValidation(discountPercent).percentValidation();
                                }

                                mylist.add(new CreateVoucher(discountPercent).createPercentDiscountVoucher());
                                createTypeRunning = false;
                                break;

                            default:
                                CreateVoucher.howMuchDiscountMessage(VoucherType);
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
