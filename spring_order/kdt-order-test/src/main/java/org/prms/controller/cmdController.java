package org.prms.controller;

import org.prms.domain.Voucher;
import org.prms.io.Console;
import org.prms.configure.AppConfiguration;
import org.prms.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class cmdController implements Runnable{
    //Controller -> Service 연결 및 등록
    private VoucherService voucherService;
    private Console console=new Console();
    private ConcurrentHashMap<UUID, Voucher> voucherList;
    // 생성자 주입을 통한 Bean 등록
    public cmdController(VoucherService voucherService) {
        this.voucherService=voucherService;
        console.cmdInit();
    }


    // Todo - 객체로
    @Override
    public void run() {
        while(true) {
            String cmd=console.input("명령어 번호 입력 (1. create 2. list 3. exit)");

            if (cmd.equals("1")) {
                create(cmd);

            }
            else if(cmd.equals("2")) {
                voucherList=voucherService.getVoucherList();
                console.cmdList(voucherList);

            }
            else if(cmd.equals("3")) {
                console.cmdExit();
            }
            else {
                console.cmdError(cmd);
            }

        }



    }
    public void create(String cmd) {
        cmd="create";
        String type=console.input("바우처 타입 입력 (1. fixed, 2. percent)");
        if (type.equals("1")) {
            fixedCreate(cmd);

        }
        else if (type.equals("2")){
            percentCreate(cmd);
        }
        else {
            console.cmdError(type);
        }
    }


    public void fixedCreate(String cmd) {
        console.cmdVoucherType(cmd);
        long price=Long.parseLong(console.input("Discount 가격 선택"));
        String type="fixed";

        voucherService.createVoucher(type,price);
        console.cmdCorrect(cmd+"/"+type+"Voucher/"+price+"원 할인 정상 수행");

    }

    public void percentCreate(String cmd) {

        Long percent=Long.parseLong(console.input("1~100 percent 선택"));
        String type="percent";

        while (true) {
            String temp=percent+"%";
            if (percent<1 || percent>100) {
                console.cmdError(temp);

                console.cmdVoucherType(cmd);
                percent=Long.parseLong(console.input("1~100 percent 선택"));

            }
            else {
                voucherService.createVoucher(type,percent);
                console.cmdCorrect(cmd+"/"+type+"Voucher/"+temp);
                break;
            }
        }

    }


}
