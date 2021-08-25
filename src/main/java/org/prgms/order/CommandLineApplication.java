package org.prgms.order;

import org.prgms.order.io.Input;
import org.prgms.order.io.Output;
import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.entity.VoucherCreateStretage;
import org.prgms.order.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.regex.Pattern;

public class CommandLineApplication implements Runnable{
    private Input input;
    private Output output;

    private VoucherService voucherService;
    private Voucher voucher;
    private VoucherCreateStretage voucherCreateStretage;


    public CommandLineApplication(Input input, Output output) {
        this.input = input;
        this.output = output;
        voucherCreateStretage = new VoucherCreateStretage();
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


        //TODO output, input 분리
        String inputVoucherString = input.input(":: ");
        if(isWrongType(inputVoucherString)){
            System.out.println("=  Insert Correct Type. PLEASE RETRY =");
            return;
        }
        String inputAmount = input.input("Insert Discount Amount :: ");

        if(isNotDigit(inputAmount)) {
            System.out.println("=  Insert Correct Amount. PLEASE RETRY =");
            return;
        }


        voucher = voucherService.insert(voucherCreateStretage.createVoucher(inputVoucherString,Long.parseLong(inputAmount)));
        System.out.println("SUCCESS >>> "+voucher.getVoucherInfo());
    }

    private boolean isWrongType(String inputVoucherString) {
        return !(inputVoucherString.contains("Fixed") || inputVoucherString.contains("Percent"));
    }


    private boolean isNotDigit(String input) {
        String pattern = "^[0-9]*$";
        return !(Pattern.matches(pattern,input) && !input.isEmpty());
    }


}
