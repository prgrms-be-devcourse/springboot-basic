package prgms.spring_week1.option;

import java.util.stream.Stream;

public enum Option {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuName;

    Option(String menuName) {
        this.menuName = menuName;
    }

    public static Option findMenuType(String inputText){
        return Stream.of(Option.values())
                .filter(option -> option.menuName.equalsIgnoreCase(inputText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid input"));
    }
}
