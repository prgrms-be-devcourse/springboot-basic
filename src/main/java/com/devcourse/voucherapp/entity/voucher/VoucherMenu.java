package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.MenuInputException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public enum VoucherMenu {
    CREATE("1", "새 할인권 생성"),
    READ_ALL("2", "전체 할인권 조회"),
    UPDATE("3", "할인권 수정"),
    DELETE("4", "할인권 삭제"),
    HOME("home", "홈으로 이동");

    private static final Map<String, VoucherMenu> VOUCHER_MENUS = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(VoucherMenu::getOption, Function.identity())));

    @Getter
    private final String option;

    private final String name;

    VoucherMenu(String option, String name) {
        this.option = option;
        this.name = name;
    }

    public static VoucherMenu from(String menuOption) {
        if (VOUCHER_MENUS.containsKey(menuOption)) {
            return VOUCHER_MENUS.get(menuOption);
        }

        throw new MenuInputException(menuOption);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", option, name);
    }
}
