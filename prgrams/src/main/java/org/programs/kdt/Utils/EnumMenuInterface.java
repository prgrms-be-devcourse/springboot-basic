package org.programs.kdt.Utils;

import java.util.Arrays;

public interface EnumMenuInterface {

    String getType();
    String getDescription();

    static <T extends EnumMenuInterface> String toMenu(T[] values) {
        StringBuilder menu = new StringBuilder("메뉴번호를 입력해주세요\n");
        Arrays.stream(values)
                .filter(value -> !value.getType().equals("error"))
                .forEach(
                        enumValue -> menu.append(enumValue.getType() + " : " + enumValue.getDescription() + "\n"));
        return menu.toString();
    }
}
