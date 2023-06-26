package org.devcourse.springbasic.controller.menu;

import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.service.VoucherService;

/** MenuType의 BiFunction 필드에서 NPE를 방지하기 위해 Exit메뉴에 따른 클래스 생성 */
public class RunByExitMenu implements RunByMenu {

    public RunByExitMenu(IODevice ioDevice, VoucherService voucherService) {}
    public void run() {}
}

