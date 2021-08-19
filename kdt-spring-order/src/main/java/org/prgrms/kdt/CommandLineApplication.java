package org.prgrms.kdt;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.*;
import java.util.Map;
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
            bw.write("명령어를 입력해주세요. \n");
            bw.flush();
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
                bw.write("Voucher 타입 번호를 입력하세요.\n 1:FixedAmount    2:PrecentDiscount\n");
                bw.flush();
                command = br.readLine();
                if(!command.equals("1")&&!command.equals("2")){
                    continue;
                }
                else if(command.equals("1")) {
                    voucherService.createVoucher("fix");

                }
                else if(command.equals("2")) {
                    voucherService.createVoucher("per");
                }
            }

            else if(command.equals("list")) {
                Map<UUID, Voucher> voucherList = voucherService.getVouchers();
                for(UUID uuid : voucherList.keySet()){
                    Voucher voc = voucherList.get(uuid);
                    bw.write(voc.toString()+"\n");
                    bw.flush();
//                    bw.write("VoucherId : "+voc.getVoucherId().toString()+"\n");
//                    bw.flush();
                }
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
