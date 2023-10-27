package team.marco.vouchermanagementsystem.application;

public enum CustomerCommandType {
    EXIT, CREATE, LIST, UPDATE, FIND_BY_ID, FIND_BY_NAME, FIND_BY_EMAIL;

    public static CustomerCommandType selectCommand(int index) {
        CustomerCommandType[] customerCommandTypes = values();

        if (index < 0 || index >= customerCommandTypes.length) {
            throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        }

        return customerCommandTypes[index];
    }
}
