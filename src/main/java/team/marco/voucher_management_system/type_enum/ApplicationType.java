package team.marco.voucher_management_system.type_enum;

import java.util.function.Consumer;
import team.marco.voucher_management_system.console_app.ConsoleApplication;
import team.marco.voucher_management_system.web_app.WebApplication;

public enum ApplicationType {
    CONSOLE(ConsoleApplication::main),
    WEB(WebApplication::main);

    private final Consumer<String[]> mainMethod;

    ApplicationType(Consumer<String[]> consumer) {
        mainMethod = consumer;
    }

    public static Consumer<String[]> getMainMethod(int index) {
        validate(index);

        return values()[index].mainMethod;
    }

    private static void validate(int index) {
        if (index < 0 || index >= values().length) {
            throw new IllegalArgumentException();
        }
    }
}
