package org.prgrms.kdt.assignments;

import org.prgrms.kdt.voucher.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Console implements ConsoleCommands{

    public static Scanner scanner = new Scanner(System.in);

    @Override
    public Optional<CommandType> inputCommand(String command) {
        try {
            return Optional.of(CommandType.valueOf(scanner.nextLine().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

    }

    @Override
    public VoucherData inputVoucher() {
        while(true){
            creatingVoucherComment();
            try {
                System.out.print("-> ");
                int voucherNumber = scanner.nextInt();
                creatingVoucherAmountComment();
                System.out.print("-> ");
                long discount = scanner.nextLong();
                return new VoucherData(voucherNumber, discount);
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException("입력 값을 확인하세요.");
            }
        }
    }

    @Override
    public void guide() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers");
        System.out.print("-> ");
    }

    @Override
    public void successfullyCreated() {
        System.out.println("바우처가 성공적으로 생성되었습니다.");
        System.out.println("");
    }

    @Override
    public void creatingVoucherComment() {
        System.out.println("생성하고자 하는 바우처를 선택해주세요.");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentAmountVoucher");

    }

    @Override
    public void creatingVoucherAmountComment() {
        System.out.println("생성할 바우처의 할인율 또는 할인금액을 숫자로 입력하세요.");
    }

    @Override
    public void commandError() {
        System.out.println("지원하지 않는 명령어입니다 입력값을 확인해주세요.");
        System.out.println("");
    }

    @Override
    public void printVoucherList(Map<UUID, Voucher> vouchers) {
        vouchers.entrySet().stream().forEach(entry -> System.out.println("Voucher Type : " + entry.getValue().toString() + ", UUID : " + entry.getValue().getVoucherID()));
        System.out.println("");
    }

    @Override
    public void moveToBack() {
        System.out.println("이전으로 이동하려면 '*'을 입력해주세요.");
    }

    @Override
    public void exit() {
        System.out.println("프로그램을 종료합니다.");
    }


}
