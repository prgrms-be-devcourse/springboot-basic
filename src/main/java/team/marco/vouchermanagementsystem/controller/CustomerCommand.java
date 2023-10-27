package team.marco.vouchermanagementsystem.controller;

public enum CustomerCommand {
    EXIT, CREATE, LIST, UPDATE, FIND_BY_ID, FIND_BY_NAME, FIND_BY_EMAIL;

    public static CustomerCommand selectCommand(int index) {
        CustomerCommand[] customerCommands = values();

        if (index < 0 || index >= customerCommands.length) {
            throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        }

        return customerCommands[index];
    }
}
