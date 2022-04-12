package org.prgrms.voucherapp.io;

/*
* Output : 출력 인터페이스
* 출력 인터페이스 또한 입력 인터페이스와 같은 고민입니다.
* */
public interface Output {
    void introMessage();

    void errorMessage(String msg);

    void exitMessage();

    void informVoucherTypeFormat();
}
