package org.prgrms.springbootbasic.repository;

public enum DBErrorMsg {

    NOTHING_WAS_INSERTED_EXP_MSG("Nothing was inserted"),
    NOTHING_WAS_DELETED_EXP_MSG("Nothing was deleted"),
    GOT_EMPTY_RESULT_MSG("Got empty result"),
    NOTING_WAS_UPDATED_EXP_MSG("Noting was updated");

    private final String message;

    DBErrorMsg(String message) {
        this.message = message;
    }

    public java.lang.String getMessage() {
        return message;
    }
}
