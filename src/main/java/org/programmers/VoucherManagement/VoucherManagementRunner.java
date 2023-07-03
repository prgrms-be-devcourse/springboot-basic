package org.programmers.VoucherManagement;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.io.CommandExecutor;
import org.programmers.VoucherManagement.io.CommandType;
import org.programmers.VoucherManagement.io.Console;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class VoucherManagementRunner implements CommandLineRunner {
    private final Console console;
    private final CommandExecutor commandExecutor;
    private final Logger logger = LoggerFactory.getLogger(VoucherManagementRunner.class);

    @Override
    public void run(String... args){
        CommandType commandType;
        boolean isRunning = true;

        while (isRunning) {
            try{
                console.printType();
                commandType = console.readType();

                if (commandType.isExit()){
                    isRunning = false;
                }
                commandExecutor.execute(commandType);

            }catch (VoucherException | IllegalArgumentException e){
                logger.info(e.getMessage());
            }
        }
    }

}
