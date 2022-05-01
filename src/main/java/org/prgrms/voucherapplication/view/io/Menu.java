package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.exception.InvalidMenuException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 메뉴 옵션을 정의한 enum class
 */
public enum Menu {
    EXIT("exit"),
    CREATE_VOUCHER_FILE("1"),
    LIST_VOUCHER_FILE("2"),
    BLACKLIST("3"),
    CREATE_CUSTOMER("4"),
    CREATE_VOUCHER("5"),
    LIST_VOUCHER_OWNED_BY_CUSTOMER("6"),
    DELETE_VOUCHER_OWNED_BY_CUSTOMER("7"),
    ISSUE_VOUCHER_TO_CUSTOMER("8"),
    CUSTOMER_WITH_SPECIFIC_VOUCHER("9");

    private final String input;

    Menu(String input) {
        this.input = input;
    }

    private static final Map<String, Menu> inputs =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect((Collectors.toMap(menu -> menu.input, Function.identity()))));

    public static Menu getMenu(String input) throws InvalidMenuException {
        return Optional.ofNullable(inputs.get(input)).orElseThrow(InvalidMenuException::new);
    }
}
