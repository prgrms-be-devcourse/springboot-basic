package W3D2.jcu;

public enum CommandStatus {
    Y("1", true),
    N("0", false);

    private String str;
    private boolean chk;

    CommandStatus(String str, boolean chk) {
        this.str = str;
        this.chk = chk;
    }

    public String getTable1Value() {
        return str;
    }

    public boolean isTable2Value() {
        return chk;
    }


}
