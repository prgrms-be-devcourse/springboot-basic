package com.example.voucherproject.common.io.console;

import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.List;

public class ConsoleOut implements Output {

    @Override
    public void exit() {
        System.out.println("종료합니다\n");
    }

    @Override
    public void home() {
        System.out.println("Return to Main Menu\n");
    }

    @Override
    public void error() {
        System.out.println("잘못된 입력값 입니다\n");
    }

    @Override
    public void createVoucher(Voucher voucher) {
        System.out.println(voucher.getType() + " 타입 바우처가 생성되었습니다\n");
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        System.out.println("저장된 바우처 수 : " + vouchers.size());
        vouchers.forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void users(List<User> users) {
        System.out.println("등록된 유저 수 : " + users.size());
        users.forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void createUser(User user) {
        System.out.println(MessageFormat.format("[{0}] {1} 유저가 생성되었습니다\n", user.getType(), user.getName()));
    }
}
