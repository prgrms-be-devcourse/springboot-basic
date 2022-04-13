package org.prgrms.springbootbasic.repository;

public class DBErrorMsg {

    public static final String NOTHING_WAS_INSERTED_EXP_MSG = "Nothing was inserted";
    public static final String GOT_EMPTY_RESULT_MSG = "Got empty result";

    private DBErrorMsg() {
        throw new AssertionError("DBErrorMsg.class는 생성할 수 없습니다.");
    }
}
