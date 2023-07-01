package kr.co.springbootweeklymission.view;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
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
    //common
    EXIT("exit", 1),

    //member
    CREATE_MEMBER("create member", 2),
    UPDATE_MEMBER("update member", 3),
    DELETE_MEMBER("delete member", 4),
    READ_MEMBER("read member", 5),
    READ_ALL_BLACK_MEMBER("read all black member", 6),

    //voucher
    CREATE_VOUCHER("create voucher", 7),
    UPDATE_VOUCHER("update voucher", 8),
    DELETE_VOUCHER("delete voucher", 9),
    READ_VOUCHER("read voucher", 10),
    READ_ALL_VOUCHERS("read all vouchers", 11);

    private final String command;
    private final int number;

    private static final Map<Integer, Command> commands =
            Collections.unmodifiableMap(Arrays.stream(values())
                    .collect(Collectors.toMap(Command::getNumber, Function.identity())));

    public static Command from(int number) {
        return Optional.ofNullable(commands.get(number))
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_COMMAND));
    }

    public boolean isCreateMember() {
        return this.number == Command.CREATE_MEMBER.getNumber();
    }

    public boolean isUpdateMember() {
        return this.number == Command.UPDATE_MEMBER.getNumber();
    }

    public boolean isDeleteMember() {
        return this.number == Command.DELETE_MEMBER.getNumber();
    }

    public boolean isReadMember() {
        return this.number == Command.READ_MEMBER.getNumber();
    }

    public boolean isReadAllBlackMember() {
        return this.number == Command.READ_ALL_BLACK_MEMBER.getNumber();
    }

    public boolean isCreateVoucher() {
        return this.number == Command.CREATE_VOUCHER.getNumber();
    }

    public boolean isUpdateVoucher() {
        return this.number == Command.UPDATE_VOUCHER.getNumber();
    }

    public boolean isDeleteVoucher() {
        return this.number == Command.DELETE_VOUCHER.getNumber();
    }

    public boolean isReadVoucher() {
        return this.number == Command.READ_VOUCHER.getNumber();
    }

    public boolean isReadAllVouchers() {
        return this.number == Command.READ_ALL_VOUCHERS.getNumber();
    }
}
