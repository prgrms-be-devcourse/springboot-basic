package org.prgrms.kdt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.voucher.Voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 11:32 오후
 */
public class Console implements Output {

    private final static String GUIDE = "Voucher_Program_Guide";
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void guide() {
        try (BufferedReader intro = Files.newBufferedReader(Paths.get(GUIDE))) {
            System.out.println(intro.lines().collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) {
            System.err.println("존재하지 않는 파일입니다.");
            System.exit(0);
        }
    }

    public CommandType inputCommand() {
        try {
            return CommandType.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.ERROR;
        }
    }

    public String inputVoucher() {
        printVoucherChoice();
        return scanner.nextLine();
    }

    private void printVoucherChoice() {
        System.out.println("생성하고 싶은 바우처를 선택하고 할인양 또는 할인율을 입력해주세요. (쉼표 ',' 를 사용하여 구분해주세요)");
        System.out.println("1. Fixed Amount Voucher");
        System.out.println("2. PercentDiscount Voucher");
        System.out.println("ex) 입력 예시 1, 1000 또는 2, 20");
    }

    public void successCreate() {
        System.out.println("성공적으로 등록되었습니다. 다음 명령을 입력해주세요.");
    }

    public void vouchers(Map<UUID, Voucher> vouchers) {
        vouchers.values()
                .forEach(System.out::println);
    }

    public void commandError() {
        System.out.println("지원하지 않는 명령어입니다. 다시 입력해주세요.");
    }
}
