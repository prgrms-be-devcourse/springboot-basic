package prgms.springbasic;

public enum CommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private String value;

    CommandType(String input) {
        this.value = input;
    }

    public String getValue() {
        return value;
    }
}
