package org.prgrms.kdtspringhomework.io;

public interface Output {
    //프로그램 시작시 출력 메시지
    void start();

    //성공했을 때 출력 메시지
    void success();

    //프로그램 종료시 출력 메시지
    void exit();

    //오류시 출력 메시지
    void inputError();
}
