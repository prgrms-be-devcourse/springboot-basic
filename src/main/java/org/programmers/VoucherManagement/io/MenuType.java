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
    INSERT_MEMBER(1),
    UPDATE_MEMBER(2),
    DELETE_MEMBER(3),
    BLACK_MEMBER_LIST(4),
    MEMBER_LIST(5),

    //Voucher
    INSERT_VOUCHER(6),
    UPDATE_VOUCHER(7),
    DELETE_VOUCHER(8),
    VOUCHER_LIST(9),

    //Wallet
    INSERT_WALLET(10),
    LIST_WALLET_BY_VOUCHER(11),
    LIST_WALLET_BY_MEMBER(12),
    DELETE_WALLET(13),

    //System
    EXIT(14);

    private static final Map<Integer, MenuType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Arrays
                    .stream(values())
                    .collect(Collectors.toMap(MenuType::getNumber, Function.identity())));

    private final int number;

    MenuType(int number) {
        this.number = number;
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
