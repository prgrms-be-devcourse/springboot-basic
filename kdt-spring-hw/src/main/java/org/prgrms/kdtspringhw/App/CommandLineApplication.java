package org.prgrms.kdtspringhw.App;


import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.blacklist.BlackListService;
import org.prgrms.kdtspringhw.configuration.AppConfiguration;
import org.prgrms.kdtspringhw.utils.BlackListReader;
import org.prgrms.kdtspringhw.utils.BlackListWriter;
import org.prgrms.kdtspringhw.voucher.Voucher;
import org.prgrms.kdtspringhw.voucher.VoucherService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.UUID;


@Component
//@SpringBootApplication
public class CommandLineApplication {
    private static AnnotationConfigApplicationContext applicationContext;
    private static VoucherService voucherService;
    private static BufferedReader br;
    private static BufferedWriter bw;
    private static BlackListService blackListService;




    private static void init() throws IOException {

        applicationContext = new AnnotationConfigApplicationContext();
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        //applicationContext.refresh();

        //프로파일 가져오기
        //applicationContext.register(AppConfiguration.class);
        //var environment = applicationContext.getEnvironment();
        //environment.setActiveProfiles("dev"); //환경설정으로 변경가능

        applicationContext.refresh();
        voucherService = applicationContext.getBean(VoucherService.class);
        blackListService = applicationContext.getBean(BlackListService.class);

        Resource resourceBlackList = applicationContext.getResource("file:csv.customer_blacklist.csv");
        blackListService.road(resourceBlackList);

    }



    public static void main(String[] args) throws IOException {

        init();
        printHome();

        while(true){
            String command = "";
            bw.write("명령어를 입력해주세요. \n");
            bw.flush();
            command = br.readLine();

            // 예외 처리시 기능이 추가되면 계속 늘어날 것이다.
            // ! command shift enter : 구문 정렬


            // ! switch 문으로 바꾸기
            if (command.equals("exit")) {
                bw.write("프로그램을 종료합니다");
                applicationContext.close();
                bw.flush();
                bw.close();
                return;
            } else if (command.equals("create")) {
                bw.write("Voucher 타입 번호를 입력하세요.\n 1:FixedAmount    2:PrecentDiscount\n");
                bw.flush();
                command = br.readLine();
                if (!command.equals("1") && !command.equals("2")) {
                    continue;
                } else if (command.equals("1")) {
                    voucherService.createVoucher("fix");

                } else if (command.equals("2")) {
                    voucherService.createVoucher("per");
                }
            } else if (command.equals("list")) {
                Map<UUID, Voucher> voucherList = voucherService.getVouchers();
                for (UUID uuid : voucherList.keySet()) {
                    Voucher voc = voucherList.get(uuid);
                    bw.write(voc.toString() + "\n");
                    bw.flush();
//                    bw.write("VoucherId : "+voc.getVoucherId().toString()+"\n");
//                    bw.flush();
                }
            } else if (command.equals("3")){
                Map<UUID, BlackList> blackLists = blackListService.getBlackLists();
                for (UUID uuid : blackLists.keySet()) {
                    BlackList blackList = blackLists.get(uuid);
                    bw.write(blackList.toString() + "\n");
                    bw.flush();
                }
            }
            else {
                bw.write("유효하지 않은 명령어 입니다. \n 명령어를 입력해주세요. \n");
                bw.flush();
                continue;

            }
        }


    }


    private static void printHome() throws IOException {
        bw.write("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers\n");
        bw.flush();
    }
}
