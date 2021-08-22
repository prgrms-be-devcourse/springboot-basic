package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

public class IoConsole implements Input, Output{

    private static Scanner sc = new Scanner(System.in);

    //생성자 주입 == autowired와 같음
//    public IoConsole(MemoryVoucherRepository memoryVoucherRepository) {
//        this.memoryVoucherRepository = memoryVoucherRepository;
//    }

    //IO console
    public static void init(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }


    public void exit(){
        System.out.println("bye bye~~");
    }


    @Override
    public String input() {
        return sc.nextLine();
    }

    @Override
    public String inputText(String s){
        System.out.println(s);
        return sc.nextLine();
    }

    @Override
    public void inputError(){
        System.out.println("Invalid input. please input again!!");
    };

    @Override
    public void outputList(List<Voucher> list){
        list.stream().forEach(m -> System.out.println(m.toString()));
        System.out.println("생성한 Voucher는 총 : "+list.size()+"개 입니다.");
    }

    @Override
    public void outputListFile(List<String> list){
        list.stream().forEach(System.out::println);
        System.out.println("생성한 Voucher는 총 : "+list.size()+"개 입니다.");
    }


    @Override
    public void message(String s) {
        System.out.println(s);
    }

}
