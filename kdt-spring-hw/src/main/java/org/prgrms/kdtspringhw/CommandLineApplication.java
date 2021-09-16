package org.prgrms.kdtspringhw;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.blacklist.BlackListService;
import org.prgrms.kdtspringhw.utils.Command;
import org.prgrms.kdtspringhw.utils.ShellPrint;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.prgrms.kdtspringhw.voucher.VoucherService;
import org.prgrms.kdtspringhw.voucher.voucherObj.VoucherType;
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
    private static ShellPrint sp;

    public CommandLineApplication(ConfigurableApplicationContext applicationContext, VoucherService voucherService, BlackListService blackListService) {
        this.applicationContext = applicationContext;
        this.voucherService = voucherService;
        this.blackListService = blackListService;
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.sp = new ShellPrint();
    }

    public void CommandLineApplicationRun() throws IOException {
        AnnotationConfigApplicationContext a;
        sp.printHome();
        while (true) {
            sp.print("명령어를 입력해주세요. \n");
            try{
                Command command = Command.getCommandType(br.readLine());
                switch (command) {
                    case EXIT:
                        sp.print("프로그램을 종료합니다");
                        applicationContext.close();
                        return;
                    case CREATE:
                        sp.print("Voucher 타입을 입력하세요.\n fix:FixedAmount    per:PrecentDiscount\n");
                        if (!voucherService.createVoucher(VoucherType.getVoucherType(br.readLine()))) {
                            continue;
                        }
                        break;
                    case LIST:
                        Map<UUID, Voucher> voucherList = voucherService.getVouchers();
                        sp.printVoucherList(voucherList);
                        break;
                    case BLACK_LIST:
                        Map<UUID, BlackList> blackLists = blackListService.getBlackLists();
                        sp.printBlackList(blackLists);
                        break;
                }
            } catch(Exception e){
                sp.print(e.getMessage());
                continue;
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLineApplicationRun();
    }
}
