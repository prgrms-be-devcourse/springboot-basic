package org.prgrms.kdt.engine;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.prgrms.kdt.Command;
import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;

import java.util.Optional;

@AllArgsConstructor
public class VoucherProgram implements Runnable {
    private Input input;
    private Output output;

    @SneakyThrows
    @Override
    public void run() {
        output.help();
        while (true) {
            String inputString = input.input("명령어를 입력하세요.");
            Optional<Command> inputCommand = parse(inputString);
            if (inputCommand.isEmpty()) {
                output.inputError();
                continue;
            }

            if (inputCommand.equals(Optional.of(Command.EXIT))) {
                break;
            }
            else if (inputCommand.equals(Optional.of(Command.CREATE))) {
                // create voucher
            }
            else if ((inputCommand.equals(Optional.of(Command.LIST)))) {
                // list voucher
            }
        }

    }

    private Optional<Command> parse(String inputString) {
        return switch (inputString) {
            case "exit" -> Optional.of(Command.EXIT);
            case "create" -> Optional.of(Command.CREATE);
            case "list" -> Optional.of(Command.LIST);
            default -> Optional.empty();
        };
    }
}
