package org.prgrms.kdtspringhw;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.blacklist.BlackListService;
import org.prgrms.kdtspringhw.utils.Command;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.prgrms.kdtspringhw.voucher.VoucherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.*;
import java.util.Map;
import java.util.UUID;


public class CommandLineApplication implements CommandLineRunner {
    private final ConfigurableApplicationContext applicationContext;
    private final VoucherService voucherService;
    private final BlackListService blackListService;
    private BufferedReader br;
    private BufferedWriter bw;

    public CommandLineApplication(ConfigurableApplicationContext applicationContext, VoucherService voucherService, BlackListService blackListService) {
        this.applicationContext = applicationContext;
        this.voucherService = voucherService;
        this.blackListService = blackListService;
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void CommandLineApplicationRun() throws IOException {
        AnnotationConfigApplicationContext a;
        printHome();
        //switch , 2중 if 문은 메소드화 (깊이가 깊어지면 가독성이 안좋아짐) , command -> enum화
        while (true) {
            //String command = "";
            bw.write("명령어를 입력해주세요. \n");bw.flush();
            Command command = Command.getCommandType(br.readLine());
            switch (command) {
                case EXIT:
                    bw.write("프로그램을 종료합니다");
                    applicationContext.close();
                    bw.flush();
                    bw.close();
                    return;
                case CREATE:
                    bw.write("Voucher 타입을 입력하세요.\n fix:FixedAmount    per:PrecentDiscount\n");
                    bw.flush();
                    command = Command.getCommandType(br.readLine());
                    if (!voucherService.createVoucher(command)) {
                        continue;
                    }
                    break;
                case LIST:
                    Map<UUID, Voucher> voucherList = voucherService.getVouchers();
                    for (UUID uuid : voucherList.keySet()) {
                        Voucher voc = voucherList.get(uuid);
                        bw.write(voc.toString() + "\n");
                        bw.flush();
                    }
                    break;
                case BLACK_LIST:
                    Map<UUID, BlackList> blackLists = blackListService.getBlackLists();
                    for (UUID uuid : blackLists.keySet()) {
                        BlackList blackList = blackLists.get(uuid);
                        bw.write(blackList.toString() + "\n");
                    }
                    bw.flush();
                    break;
                default:
                    bw.write("유효하지 않은 명령어 입니다.\n");
                    bw.flush();
                    continue;
            }
        }
    }


    private void printHome() throws IOException {
        bw.write("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers\n" +
                "Type black to list all black_list\n"+
                "\n"
        );
        bw.flush();
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLineApplicationRun();
    }
}
