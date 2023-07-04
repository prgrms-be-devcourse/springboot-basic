package prgms.spring_week1.menu;

import prgms.spring_week1.exception.NoSuchOptionValueException;

import java.util.stream.Stream;

public enum Menu {
    INVALID,
    EXIT,
    CREATE,
    LIST,
    BLACK;

    public static Menu findMenuType(String inputText) throws NoSuchOptionValueException {
        return Stream.of(Menu.values())
                .filter(menu -> menu.name().equalsIgnoreCase(inputText))
                .findFirst()
                .orElseThrow(() -> new NoSuchOptionValueException("해당 메뉴 타입이 존재하지 않습니다."));
    }
}
