package org.prms.io;

import org.prms.domain.Voucher;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Console implements Input, Output{

    private final Scanner scan=new Scanner(System.in);

//  시작
    @Override
    public void cmdInit() {
        System.out.println("=== Voucher Program");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to all vouchers");
    }

// 입력
    @Override
    public String input(String cmd) {
        System.out.println("실행된 명렁어:"+cmd);
        return scan.nextLine();
    }

//    @Override
//    public long number(int num) {
//        return scan.nextLong(num);
//    }


// 출력
    @Override
    public void cmdError(String cmd) {
        System.out.println(cmd+"는 잘못된 명령어입니다.");
        System.out.println("다른 명령어를 넣어주세요");
    }

    @Override
    public void cmdCorrect(String cmd) {
        System.out.println(cmd +"가 실행되었습니다.");
    }




//
    @Override
    public void cmdList(ConcurrentHashMap<UUID,Voucher> voucherList) {
        System.out.println("Voucher List");
        for (UUID id:voucherList.keySet()) {
            System.out.println(voucherList.get(id).toString());
        }
    }



    @Override
    public void cmdExit() {

        System.out.println("시스템 종료");
        System.exit(0);

    }

    @Override
    public void cmdVoucherType(String cmd) {
        System.out.println(cmd +" 선택");

    }

}
