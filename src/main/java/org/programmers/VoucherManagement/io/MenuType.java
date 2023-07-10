package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_EXIST_COMMAND;

public enum MenuType {
    //Member
    INSERT_MEMBER("insertmember", 1),
    UPDATE_MEMBER("updatemember", 2),
    DELETE_MEMBER("deletemember", 3),
    BLACK_MEMBER_LIST("blackmemberlist", 4),
    MEMBER_LIST("memberlist", 5),


    //Voucher
    INSERT_VOUCHER("insertvoucher", 6),
    UPDATE_VOUCHER("updatevoucher", 7),
    DELETE_VOUCHER("deletevoucher", 8),
    VOUCHER_LIST("voucherlist", 9),

    //System
    EXIT("exit", 10);


    private static final Map<Integer, MenuType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Arrays
                    .stream(values())
                    .collect(Collectors.toMap(MenuType::getNumber, Function.identity())));

    private final String type;
    private final int number;

    MenuType(String type, int number) {
        this.type = type;
        this.number = number;
    }

    private String getType() {
        return type;
    }

    private int getNumber() {
        return number;
    }


    public static MenuType from(int type) {
        if (!COMMAND_TYPE_MAP.containsKey(type)) {
            throw new VoucherException(NOT_EXIST_COMMAND);
        }
        return COMMAND_TYPE_MAP.get(type);
    }
}
