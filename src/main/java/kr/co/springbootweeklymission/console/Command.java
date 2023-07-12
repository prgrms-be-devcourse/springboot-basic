package kr.co.springbootweeklymission.console;

import kr.co.springbootweeklymission.global.error.exception.NotFoundException;
import kr.co.springbootweeklymission.global.response.ResponseStatus;
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
    EXIT("프로그램 종료", 1),

    //member
    CREATE_MEMBER("회원 생성", 2),
    UPDATE_MEMBER("회원 수정", 3),
    DELETE_MEMBER("회원 삭제", 4),
    READ_MEMBER("회원 조회", 5),
    READ_ALL_BLACK_MEMBER("모든 블랙 회원 조회", 6),

    //voucher
    CREATE_VOUCHER("바우처 생성", 7),
    UPDATE_VOUCHER("바우처 수정", 8),
    DELETE_VOUCHER("바우처 삭제", 9),
    READ_VOUCHER("바우처 조회", 10),
    READ_ALL_VOUCHERS("모든 바우처 조회", 11),

    //voucher_member
    CREATE_VOUCHER_MEMBER("고객에게 바우처를 할당", 12),
    READ_VOUCHERS_BY_MEMBER("고객의 모든 바우처 조회", 13),
    READ_MEMBER_BY_VOUCHER("해당 바우처를 가진 특정 고객 조회", 14),
    DELETE_VOUCHER_MEMBER("고객이 가진 바우처 삭제", 15);

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
        return this == Command.CREATE_MEMBER;
    }

    public boolean isUpdateMember() {
        return this == Command.UPDATE_MEMBER;
    }

    public boolean isDeleteMember() {
        return this == Command.DELETE_MEMBER;
    }

    public boolean isReadMember() {
        return this == Command.READ_MEMBER;
    }

    public boolean isReadAllBlackMember() {
        return this == Command.READ_ALL_BLACK_MEMBER;
    }

    public boolean isCreateVoucher() {
        return this == Command.CREATE_VOUCHER;
    }

    public boolean isUpdateVoucher() {
        return this == Command.UPDATE_VOUCHER;
    }

    public boolean isDeleteVoucher() {
        return this == Command.DELETE_VOUCHER;
    }

    public boolean isReadVoucher() {
        return this == Command.READ_VOUCHER;
    }

    public boolean isReadAllVouchers() {
        return this == Command.READ_ALL_VOUCHERS;
    }

    public boolean isCreateVoucherMember() {
        return this == Command.CREATE_VOUCHER_MEMBER;
    }

    public boolean isReadVouchersByMember() {
        return this == Command.READ_VOUCHERS_BY_MEMBER;
    }

    public boolean isReadMemberByVoucher() {
        return this == Command.READ_MEMBER_BY_VOUCHER;
    }

    public boolean isDeleteVoucherMember() {
        return this == Command.DELETE_VOUCHER_MEMBER;
    }
}
