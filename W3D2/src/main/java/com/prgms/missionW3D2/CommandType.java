package com.prgms.missionW3D2;

import com.prgms.missionW3D2.voucher.Voucher;
import com.prgms.missionW3D2.voucher.VoucherService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public enum CommandType {

    EXIT("exit"){
        void doCommand(VoucherService voucherService){System.exit(0);}
    },
    CREATE("create"){
        void doCommand(VoucherService voucherService) {
            System.out.println(
                    "Type 1 to create Fixed Amount Voucher.\n" +
                            "Type 2 to create Percent Discount Voucher.\n" +
                            "Type exit to re-command."
            );
            System.out.printf(">> ");
            Scanner sc = new Scanner(System.in);
            String createCommand = sc.nextLine();
            CreateCommandType.of(createCommand).doCommand(voucherService);
        }
    },
    LIST("list"){
        void doCommand(VoucherService voucherService) {
            List<Voucher> list = voucherService.getVoucherList();
            if (list.size() == 0) {
                System.out.println("There's no voucher. Create a voucher.");
            } else {
                list.stream().forEach(a -> System.out.println(a.getVoucherInfo()));
            }
        }
    };

    // note : 이게 없으면 CommandType.of(command).doCommand(voucherService); 가 안 되던데 왜지?
    abstract void doCommand(VoucherService voucherService);

    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType of(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.getCommand().equals(command))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("잘못된 command를 입력하셨습니다");
                    return CommandType.EXIT;
                });
    }
}
