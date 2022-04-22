package com.pppp0722.vouchermanagement.engine;

import com.pppp0722.vouchermanagement.engine.command.CommandType;
import com.pppp0722.vouchermanagement.engine.command.Create;
import com.pppp0722.vouchermanagement.engine.command.Delete;
import com.pppp0722.vouchermanagement.engine.command.Read;
import com.pppp0722.vouchermanagement.engine.command.Update;
import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.service.MemberServiceImpl;
import com.pppp0722.vouchermanagement.voucher.service.VoucherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = Console.getInstance();
    private final VoucherServiceImpl voucherService;
    private final MemberServiceImpl memberService;
    private final Create create;
    private final Read read;
    private final Update update;
    private final Delete delete;

    public CommandLineApplication(VoucherServiceImpl voucherService,
        MemberServiceImpl memberService) {
        this.voucherService = voucherService;
        this.memberService = memberService;
        create = new Create(memberService, voucherService);
        read = new Read(memberService, voucherService);
        update = new Update(memberService, voucherService);
        delete = new Delete(memberService, voucherService);
    }

    public void run() {
        logger.info("Start voucher management application.");
        console.printLogo();

        boolean isExit = false;
        while (!isExit) {
            console.printMenu();
            String command = console.getCommand("Input : ");
            CommandType commandType = CommandType.getCommandType(command);
            switch (commandType) {
                case CREATE:
                    create.start();
                    break;
                case READ:
                    read.start();
                    break;
                case UPDATE:
                    update.start();
                    break;
                case DELETE:
                    delete.start();
                    break;
                case EXIT:
                    isExit = true;
                    break;
                default:
                    console.printInputError();
                    logger.error("Invalid command -> {}!", command);
                    break;
            }
            System.out.println();
        }

        logger.info("Terminate voucher management application.");
    }
}