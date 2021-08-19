package org.prgms.w3d1;

import org.prgms.w3d1.io.Input;
import org.prgms.w3d1.io.Output;
import org.prgms.w3d1.model.blacklist.BlacklistService;
import org.prgms.w3d1.model.voucher.VoucherService;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;
import java.util.UUID;


public class CommandLineApplication implements Runnable{

    private Input input;
    private Output output;

    public CommandLineApplication(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        var blacklistService = applicationContext.getBean(BlacklistService.class);

        while (true){
            output.printMenual();
            String command = sc.nextLine();
            switch (command) {
                case "exit" -> {
                    System.exit(0);
                }
                case "create" -> {
                    output.printCreateMenu();
                    command = sc.nextLine();
                    if (command.equals("1")) {
                        output.printFixedMenu();
                        voucherService.saveVoucher(VoucherType.FIXED_AMOUNT_TYPE, sc.nextLong());
                    } else if(command.equals("2")) {
                        output.printPercentMenu();
                        voucherService.saveVoucher(VoucherType.PERCENT_DISCOUNT_TYPE, sc.nextLong());
                    }else{
                        output.inputError();
                        continue;
                    }
                    sc.nextLine();
                }
                case "list" -> {
                    System.out.println(voucherService.findAll());
                }

                case "blacklist" -> {
                    output.printBlackListMenu();
                    command = sc.nextLine();
                    switch (command){
                        case "exit" -> {
                            System.exit(0);
                        }
                        case "create" -> {
                            output.printBlackListCreateMenu();
                            String name = sc.nextLine();
                            blacklistService.save(UUID.randomUUID(), name);
                        }
                        case "list" -> {
                            System.out.println(blacklistService.findAll());
                        }

                        default -> {
                            output.inputError();
                        }
                    }
                }
                default -> {
                    output.inputError();
                }
            }
        }
    }
}
