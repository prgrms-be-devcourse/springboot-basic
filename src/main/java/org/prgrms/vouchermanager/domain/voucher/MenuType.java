package org.prgrms.vouchermanager.domain.voucher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.exception.InputValueException;

@Slf4j
public enum MenuType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    FIXED("fixed"),
    PERCENT("percent"),
    FIND("find"),
    REMOVE("remove"),
    EMAIL("email"),
    VOUCHER("voucher");
    private final String value;

    MenuType(String value) {
        this.value = value;
    }


    public static MenuType fromValue(String value) {
        for (MenuType menu : MenuType.values()) {
            if (menu.value.equalsIgnoreCase(value)) {
                return menu;
            }
        }
        log.error("입력값 : {}", value);
        throw new InputValueException();
    }
}
