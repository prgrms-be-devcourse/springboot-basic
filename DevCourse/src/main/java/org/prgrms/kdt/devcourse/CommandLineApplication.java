package org.prgrms.kdt.devcourse;

import org.prgrms.kdt.devcourse.customer.Customer;
import org.prgrms.kdt.devcourse.customer.CustomerService;
import org.prgrms.kdt.devcourse.io.Console;
import org.prgrms.kdt.devcourse.voucher.Voucher;
import org.prgrms.kdt.devcourse.voucher.VoucherService;
import org.prgrms.kdt.devcourse.voucher.VoucherType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class CommandLineApplication {


    public static void main(String[] args) {

        final String CMD_CREATE = "create";
        final String CMD_LIST = "list";
        final String CMD_BLACK_LIST = "black";
        final String CMD_EXIT = "exit";

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        var customerService = applicationContext.getBean(CustomerService.class);
        var info = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type black to list black customer.
                """;

        Console console = new Console();
        while (true){
            String cmd = console.input(info);
            switch (cmd) {
                case CMD_CREATE -> {
                    createVoucher(console,voucherService);
                }
                case CMD_LIST -> {
                    getVoucherList(console,voucherService.getAllVouchers());
                }
                case CMD_BLACK_LIST-> {
                    getBlackCustomerList(console,customerService.getBlackCustomers());
                }
                case CMD_EXIT -> {
                    console.printOut("프로그램을 종료합니다.");
                    applicationContext.close();
                    System.exit(0);
                }
                default -> console.inputError(cmd);
            }
        }

    }

    public static void createVoucher(Console console, VoucherService voucherService){
        String voucherInput = console.input("고정 값 바우처: FIXED, 퍼센트 할인 바우처: PERCENT 입력");

        if (VoucherType.isExistType(voucherInput)) {
            VoucherType type = VoucherType.valueOf(voucherInput);

            String amountInput = console.input("원하는 바우처 값 입력");
            if(validateAmount(amountInput,type)){
                long discountAmount = Long.parseLong(amountInput);
                voucherService.createVoucher(type, discountAmount);
            }
            else{
                console.inputError(amountInput);
            }

        }else{
            console.inputError(voucherInput);
        }
    }

    public static void getVoucherList(Console console, List<Voucher> vouchers){
        console.printVoucherList(vouchers);
    }

    public static void getBlackCustomerList(Console console, List<Customer> customers){
        console.printCustomerList(customers);
    }

    public static boolean validateAmount(String inputAmount, VoucherType voucherType){
        try{
            long amount = Long.parseLong(inputAmount);
            if(amount<=0)
                return false;
            if(voucherType==VoucherType.PERCENT && amount>100)
                return false;
        }catch (NumberFormatException numberFormatException){
            return false;
        }
        return true;
    }



}
