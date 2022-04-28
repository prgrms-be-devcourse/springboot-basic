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
    private final Create create;
    private final Read read;
    private final Update update;
    private final Delete delete;

    public CommandLineApplication(VoucherServiceImpl voucherService,
        MemberServiceImpl memberService) {
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
            CommandType commandType;
            try {
                commandType = console.inputCommandType();
            } catch (IllegalArgumentException e) {
                logger.error("Invalid command type!", e);
                console.printInputError();
                continue;
            }
            switch (commandType) {
                case CREATE:
                    create.create();
                    break;
                case READ:
                    read.read();
                    break;
                case UPDATE:
                    update.update();
                    break;
                case DELETE:
                    delete.delete();
                    break;
                case EXIT:
                    isExit = true;
                    break;
                default:
                    break;
            }
            System.out.println();
        }

        logger.info("Terminate voucher management application.");
    }
}