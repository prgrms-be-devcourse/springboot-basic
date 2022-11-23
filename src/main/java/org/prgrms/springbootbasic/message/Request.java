package org.prgrms.springbootbasic.message;

import org.prgrms.springbootbasic.type.Menu;
import org.prgrms.springbootbasic.type.TypeOption;

import java.util.concurrent.ConcurrentHashMap;

public class Request {

    private final Menu menuItem;
    private TypeOption option;

    private final ConcurrentHashMap<String, Long> arguments = new ConcurrentHashMap<>();

    private Request(Menu menuItem) {
        this.menuItem = menuItem;
    }

    public static Request GenerateRequest(String input) {
        Menu menuItem = Menu.valueOf(input.toUpperCase());
        return new Request(menuItem);
    }

    public Menu getMenuItem() {
        return menuItem;
    }

    public Long getArgument(String argument) {
        return arguments.get(argument);
    }

    public TypeOption getOption() {
        return option;
    }

    public void setOption(TypeOption option) {
        this.option = option;
    }

    public void insertArgument(String argument, long value) {
        arguments.put(argument, value);
    }

    public boolean isCreateRequest() {
        return menuItem == Menu.CREATE;
    }

    public boolean isLookupRequest() {
        return menuItem == Menu.LIST;
    }
}
