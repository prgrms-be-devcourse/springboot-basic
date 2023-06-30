package com.prgrms.springbootbasic.io;

public interface Input {

    //콘솔 명령어 입력
    String readCommand(String message);

    //바우처 타입
    String readVoucherType(String message);

    //바우처 금액
    String readVoucherValue(String message);
}
