package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.util.UUID;

public class CommandLineApplication {
    private static AnnotationConfigApplicationContext applicationContext;
    private static VoucherService voucherService;
    private static BufferedReader br;
    private static BufferedWriter bw;

    public static void main(String[] args) throws IOException {
        CommandLineApplication cla = new CommandLineApplication();
        cla.init();
        cla.printHome();

        while(true){
            String command = "";
            command = br.readLine();

            if(!command.equals("exit")&&!command.equals("create")&&!command.equals("list")){
                bw.write("유효하지 않은 명령어 입니다. \n 명령어를 입력해주세요. \n");
                bw.flush();
                continue;
            }

            else if(command.equals("exit")) {
                bw.write("프로그램을 종료합니다");
                bw.flush();
                bw.close();
                return;
            }

            else if(command.equals("create")){

            }

            else {

            }

        }



    }

    private static void init(){
        applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = applicationContext.getBean(VoucherService.class);
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }
    private void printHome() throws IOException {
        bw.write("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers\n");
        bw.flush();
    }

}
