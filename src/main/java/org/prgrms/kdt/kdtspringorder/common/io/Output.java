package org.prgrms.kdt.kdtspringorder.common.io;

/**
 * 출력 인터페이스
 */
public interface Output {

    /**
     * 어플리케이션 시작 시 메시지를 출력합니다.
     */
    public void showFirstMsg();

    /**
     * 잘못 입력된 커맨드에 대한 안내 메시지를 출력합니다.
     */
    public void showIncorrectCmdMsg();

    /**
     * 종료 메시지를 출력합니다.
     */
    public void showExitMsg();

    /**
     * 잘못 입력된 바우처 타입 번호에 대한 안내 메시지를 출력합니다.
     */
    public void showIncorrectNumMsg();

}
