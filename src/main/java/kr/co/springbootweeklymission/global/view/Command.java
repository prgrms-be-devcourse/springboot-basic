package kr.co.springbootweeklymission.global.view;

import kr.co.springbootweeklymission.global.error.exception.NotFoundException;
import kr.co.springbootweeklymission.global.error.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Command {
    EXIT("exit"),
    LIST_BLACK_MEMBER("blackList"),
    CREATE_VOUCHER("createVoucher"),
    LIST_VOUCHER("voucherList");

    private final String command;

    private static final Map<String, Command> commands =
            Collections.unmodifiableMap(Arrays.stream(values())
                    .collect(Collectors.toMap(Command::getCommand, Function.identity())));

    public static Command from(String command) {
        return Optional.ofNullable(commands.get(command))
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_COMMAND));
    }

    public boolean isBlackList(){
        return this.command
                .equals(Command.LIST_BLACK_MEMBER.getCommand());
    }

    public boolean isCreateVoucher(){
        return this.command
                .equals(Command.CREATE_VOUCHER.getCommand());
    }

    public boolean isVoucherList(){
        return this.command
                .equals(Command.LIST_VOUCHER.getCommand());
    }
}
