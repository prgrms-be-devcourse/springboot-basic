package org.programs.kdt;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Command.CommandMenu;
import org.programs.kdt.IO.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.programs.kdt.Exception.ErrorCode.INVALID_COMMAND_TYPE;
import static org.programs.kdt.Exception.ErrorCode.INVALID_ENUM_TYPE;

@Component
@RequiredArgsConstructor
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final Console console;
    private final VoucherApp voucherApp;
    private final CustomerApp customerApp;
    private final WalletApp walletApp;

    public void run()  {

        CommandMenu command =  CommandMenu.ERROR;
        while(!command.equals(CommandMenu.EXIT)) {
            console.output(CommandMenu.toMenu());
            String input = console.input("명령어를 입력해주세요/");
            command = CommandMenu.findCommand(input);
            menuProcess(command);
        }
    }

    private void menuProcess(CommandMenu command) {
        switch (command) {
            case VOUCHER:
                voucherApp.voucherProcess();
                break;
            case CUSTOMER:
                customerApp.customerProcess();
                break;
            case ERROR:
                logger.error(INVALID_COMMAND_TYPE.getMessage());
                break;
            case WALLET:
                walletApp.walletProcess();
                break;
            case EXIT:
                break;
            default:
                logger.error(INVALID_ENUM_TYPE.getMessage());
        }
    }


}
