package org.prgrms.kdt.test;
import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.UUID;


@Component
public class CommandLineApplication {
    private static AnnotationConfigApplicationContext applicationContext;
    private static VoucherService voucherService;
    private static BufferedReader br;
    private static BufferedWriter bw;


    private static void init(){
        applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = applicationContext.getBean(VoucherService.class);
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
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
