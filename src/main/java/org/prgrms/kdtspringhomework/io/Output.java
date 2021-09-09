package org.prgrms.kdtspringhomework.io;

public interface Output {
    //프로그램 시작시 출력 메시지
    void start();

    //동작 입력 메시지
    void inputCommandTypeMessage();

    //voucher 타입 메시지
    void inputVoucherTypeMessage();

    //금액 입력 메시지
    void inputAmountMessage();

    //성공했을 때 출력 메시지
    void success();

    //프로그램 종료시 출력 메시지
    void exit();

    //커맨드 입력 오류
    void invalidCommandType();

    //타입 입력 오류
    void invalidVoucherType();

    //금액 입력 오류
    void invalidAmount();
}
