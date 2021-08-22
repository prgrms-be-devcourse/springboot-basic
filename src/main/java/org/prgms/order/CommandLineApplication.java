package org.prgms.order;

import org.prgms.order.io.Input;
import org.prgms.order.io.Output;
import org.prgms.order.voucher.entity.FixedAmountVoucher;
import org.prgms.order.voucher.entity.PercentDiscountVoucher;
import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;
import java.util.regex.Pattern;

public class CommandLineApplication implements Runnable{
    private Input input;
    private Output output;

    private VoucherService voucherService;
    private Voucher voucher;


    public CommandLineApplication(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = applicationContext.getBean(VoucherService.class);

        while(true){
            output.mainMenu();
            String inputString = input.input(":: ");

            switch (inputString) {
                case "create" -> createVoucher();
                case "list" -> printVoucherList();
                case "exit" -> {
                    System.out.println("=== Exit Program ===\n");
                    return;
                }
                default -> System.out.println("= RETRY =");
            }
        }

    }


    private void printVoucherList() {
        output.voucherList(voucherService);
    }

    private void createVoucher() {
        output.selectVoucher();
        String inputVoucherString = input.input(":: ");

        switch (inputVoucherString) {
            case "Fixed" -> {
                String inputAmount = input.input("insert discount amount :: ");

                if(isDigit(inputAmount)) {
                    voucher = voucherService.insert(
                            new FixedAmountVoucher(UUID.randomUUID(),
                            Long.parseLong(inputAmount)));
                    System.out.println("SUCCESS >>> "+voucher.getVoucherInfo());
                }else{
                    System.out.println("= Only numbers can be entered. PLEASE RETRY =");
                }

            }
            case "Percent" -> {
                String inputAmount = input.input("insert discount percent(1 ~ 100):: ");

                if(isDigit(inputAmount)){
                    long percentAmount = Long.parseLong(inputAmount);
                    if(isPercent(percentAmount)){
                        voucher = voucherService.insert(new PercentDiscountVoucher(UUID.randomUUID(), percentAmount));
                        System.out.println("SUCCESS >>> "+voucher.getVoucherInfo());
                    }else{
                        System.out.println("= Out of range. PLEASE RETRY =");
                    }
                }else{
                    System.out.println("= Only numbers can be entered. PLEASE RETRY =");
                }

            }
            default -> System.out.println("= RETRY =");
        }
    }

    private boolean isPercent(Long percentAmount) {
        return percentAmount <= 100 && percentAmount > 0;
    }

    private boolean isDigit(String input) {
        String pattern = "^[0-9]*$";
        return Pattern.matches(pattern,input) && !input.isEmpty();
    }


}
