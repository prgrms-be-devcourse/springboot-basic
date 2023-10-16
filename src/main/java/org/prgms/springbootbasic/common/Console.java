package org.prgms.springbootbasic.common;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.Voucher;
import org.prgms.springbootbasic.repository.VoucherRepository;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
@Slf4j
public class Console {
    private static final String EXIT = "exit";
    private static final String LIST = "list";
    private static final String CREATE = "create";
    private static final Scanner CONSOLE_INPUT = new Scanner(System.in);

    private final VoucherRepository voucherRepository;

    public Console(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void run(){
        log.info("voucherRepository = {}", voucherRepository.getClass().getCanonicalName());
        String cmd = "";
        while (!cmd.equals(EXIT)){

            System.out.println("=== Voucher Program ===");
            System.out.println("Type 'exit' to exit the program.");
            System.out.println("Type 'create' to create a new voucher.");
            System.out.println("Type 'list' to list all vouchers.");

            try {
                cmd = CONSOLE_INPUT.next();

                log.info("cmd input is {}", cmd);

                checkCmdValidation(cmd);
            }catch (IllegalArgumentException e){
                System.out.println("Invalid argument. Type command again.");
            }
            catch (RuntimeException e){
                System.out.println("Invalid input or system error. redirect to beginning.");
            }

            System.out.println();
        }
    }

    private void checkCmdValidation(String cmd) {
        switch (cmd) {
            case CREATE -> create();
            case LIST -> list();
            case EXIT -> {}
            default -> {
                log.warn("invalid cmd. now cmd = {}", cmd);
                throw new IllegalArgumentException("Invalid command. Type command again.");
            }
        }
    }

    public void create(){
        System.out.println();
        System.out.println("Which voucher would you like to create? Just type number.");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        try{
            int type = CONSOLE_INPUT.nextInt();
            VoucherType voucherType = VoucherType.getTypeFromSeq(type);

            System.out.println();

            int discountVal = getDiscountVal(voucherType);
            voucherRepository.create(voucherType, discountVal);
        }catch (InputMismatchException e){
            String invalidVal = CONSOLE_INPUT.nextLine();
            log.warn("User input = {}", invalidVal);
            throw new IllegalArgumentException("Not integer.");
        }catch (NoSuchElementException e){
            log.error("input is exhausted");
            throw new RuntimeException("Input is exhausted");
        }catch (IllegalStateException e){
            log.error("Scanner is closed");
            throw new RuntimeException("Scanner is closed.");
        }
    }

    public void list(){
        List<Voucher> vouchers = voucherRepository.findAll();
        System.out.println();
        vouchers.forEach(v -> System.out.println(MessageFormat.format("id: {0}, type: {1}", v.getVoucherId(), v.getClass().getSimpleName())));
        System.out.println();
    }

    private int getDiscountVal(VoucherType type){
        int val;

        switch (type){
            case FIXED_AMOUNT -> System.out.println("Enter the fixed discount amount.");
            case PERCENT_DISCOUNT -> System.out.println("Enter the discount percentage.");
        }
        val = CONSOLE_INPUT.nextInt();

        return val;
    }
}
