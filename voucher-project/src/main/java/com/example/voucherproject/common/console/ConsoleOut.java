package com.example.voucherproject.common.console;

import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.voucher.domain.Voucher;
import com.example.voucherproject.wallet.domain.Wallet;

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
    }

    @Override
    public void printUsers(List<User> users) {
        System.out.println("등록된 유저 수 : " + users.size());
        for(int i=1;i<=users.size();i++){
            System.out.printf("%d. ",i);
            System.out.println(users.get(i-1));
        }
    }

    @Override
    public void printWallets(List<Wallet> wallets) {
        System.out.println("등록된 Wallet 수 : " + wallets.size());
        for(int i=1;i<=wallets.size();i++){
            System.out.printf("%d. ",i);
            System.out.println(wallets.get(i-1));
        }
    }

    @Override
    public void createUser(User user) {
        System.out.println(MessageFormat.format("[{0}] {1} 유저가 생성되었습니다\n", user.getType(), user.getName()));
    }

    @Override
    public void selectedUser(User user) {
        System.out.println("selected user : "+ user);
        System.out.println();
    }

    @Override
    public void selectedVoucher(Voucher voucher) {
        System.out.println("selected voucher : "+ voucher);
        System.out.println();
    }

    @Override
    public void deleteSuccess(Wallet wallet) {
        System.out.println("[삭제 성공] : "+ wallet);
    }

    @Override
    public void printWalletVouchers(List<Wallet> wallets) {
        System.out.println("보유하고 있는 바우처 수 : "+ wallets.size());
        for(int i=0;i<wallets.size();i++){
            System.out.printf("%d. voucherId : ",i+1);
            System.out.println(wallets.get(i).getVoucherId());
        }
        System.out.println();
    }

    @Override
    public void printWalletUsers(List<Wallet> wallets) {
        System.out.println("바우처를 보유한 유저 수 : "+ wallets.size());
        for(int i=0;i<wallets.size();i++){
            System.out.printf("%d. userId : ",i+1);
            System.out.println(wallets.get(i).getUserId());
        }
        System.out.println();
    }
}
