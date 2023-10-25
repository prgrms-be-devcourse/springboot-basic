package org.prgrms.vouchermanager.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class Output {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public void selectMenu(){
        System.out.println("Select menu\n" + "1. Voucher menu\n" + "2. Customer menu\n" + "3.Wallet menu");
    }
    public void voucherInit(){
        System.out.println("=== Voucher Program ===\n" + "Type **exit** to exit the program.\n" + "Type **create** to create a new voucher.\n" + "Type **list** to list all vouchers.\n" + "```");
    }

    public void createVoucherMenu(){
        System.out.println("=== Create Voucher Menu ===\n" + "Select Voucher Mode(Fixed or Percent)");
    }

    public void customerInit(){
        System.out.println("===Customer Program ===\n" + "Type **exit** to exit the program.\n" + "Type **create** to create a new customer.\n" + "Type **list** to list all customers.\n" + "```");
    }

    public void print(String message){
        System.out.println(message);
    }

    public void outputCustomerName(){
        System.out.println("고객의 이름을 기입해주세요");
    }
    public void outputCustomerEmail(){
        System.out.println("고객의 이메일을 기입해주세요");
    }
    public void outputCustomerisBlack(){
        System.out.println("고객의 블랙리스트 여부를 기입해주세요(true or false");
    }
    //------------wallet 관련 output
    public void walletInit(){
        System.out.println("=== Wallet Program ===\n" +
                "Type **exit** to exit the program.\n" +
                "Type **create** to create a new wallet info\n" +
                "Type **remove** to remove customer's voucher\n" +
                "Type **find** to find customers with specific setting");
    }
    public void outputWalletEmail(){
        System.out.println("지갑에 등록할 고객의 이메일을 기입해주세요");
    }
    public void outputEmailNotExist(){
        System.out.println("존재하지 않는 고객의 이메일입니다.");
    }

    public void outputWalletVoucher(){
        System.out.println("지갑 내 찾을 바우처 아이디를 기입해주세요");
    }
    public void outputWalletRemove(){
        System.out.println("바우처를 제거할 고객의 이메일을 기입해주세요");
    }
    public void outputWalletFind(){
        System.out.println("조회하려는 바우처 타입을 기입해주세요(Fixed or Percent");
    }
    public void outputFindWithMenu(){
        System.out.println("지갑 조회를 이메일을 통해 할 지, 바우처를 통해 할 지 선택해주세요.");
    }






}
