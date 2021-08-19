package org.prms.controller;

import org.prms.io.Console;
import org.prms.service.AppConfiguration;
import org.prms.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class cmdController implements Runnable{
    //Controller -> Service 연결 및 등록
    private VoucherService voucherService;
    private Console console=new Console();

    // 생성자 주입을 통한 Bean 등록
    public cmdController() {
        var applicationContext=new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.voucherService = applicationContext.getBean(VoucherService.class);
        console.cmdInit();
    }


    // Todo - 객체로
    @Override
    public void run() {
        while(true) {
            String cmd=console.input("명령어 번호 입력 (1. create 2. list 3. exit)");

            if (cmd.equals("1")) {
                cmd="create";
                String type=console.input("바우처 타입 입력 (1. fixed, 2. percent)");
                if (type.equals("1")) {
                    System.out.println("fixed를 선택");
                    long price=Long.parseLong(console.input("Discount 가격 선택"));
                    type="fixed";

                    voucherService.createVoucher(type,price);
                    console.cmdCorrect(cmd+"/"+type+"Voucher/"+price+"원 할인 정상 수행");
                }
                else if (type.equals("2")){
                    System.out.println("percent 선택");
                    Long percent=Long.parseLong(console.input("1~100 percent 선택"));
                    type="percent";

                    while (true) {
                        String temp=percent+"%";
                        if (percent<1 || percent>100) {
                            console.cmdError(temp);

                            System.out.println("percent 선택");
                            percent=Long.parseLong(console.input("1~100 percent 선택"));

                        }
                        else {
                            voucherService.createVoucher(type,percent);
                            console.cmdCorrect(cmd+"/"+type+"Voucher/"+temp);
                            break;
                        }
                    }

                }
                else {
                    console.cmdError(type);
                }

            }
            else if(cmd.equals("2")) {
                console.cmdList(voucherService.getVoucherList());

            }
            else if(cmd.equals("3")) {
                console.cmdExit();
            }
            else {
                console.cmdError(cmd);
            }

        }



    }

    // AppConfiguration을 사용하기 위해



}
