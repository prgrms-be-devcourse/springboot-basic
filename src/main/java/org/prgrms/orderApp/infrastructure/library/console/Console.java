package org.prgrms.orderApp.infrastructure.library.console;

import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String scipt) {
        System.out.print(scipt);
        return scanner.nextLine();
    }


    // console 패키지는 독립적으로 사용되어야 함으로 아래의 voucherList 함수는 수정 필요함.
    @Override
    public void showList(List<Object> listData) {
        listData.forEach(System.out::println);
    }

    @Override
    public void errorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    @Override
    public void infoMessage(String infoMessage) {
        System.out.println(infoMessage);
    }

    @Override
    public void print(String message){
        System.out.println(message);
    }
}
