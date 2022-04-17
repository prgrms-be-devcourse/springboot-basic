package org.prgrms.deukyun.voucherapp.app.menu;

/**
 * 메뉴
 */
public interface Menu {

    /**
     * 지원가능한 명령어 디스플레이
     */
    void display();

    /**
     * 로직 실행
     */
    void proc();
}
