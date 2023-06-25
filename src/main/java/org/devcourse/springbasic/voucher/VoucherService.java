package org.devcourse.springbasic.voucher;

import org.devcourse.springbasic.menu.RunByMenu;
import org.devcourse.springbasic.menu.MenuType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VoucherService {

    private final Map<String, RunByMenu> runBeanMapByMenu;
    public VoucherService(Map<String, RunByMenu> runBeanMapByMenu) {
        this.runBeanMapByMenu = runBeanMapByMenu;
    }

    public void runBy(MenuType menuType) {

        if(menuType == MenuType.EXIT) return;

        RunByMenu runByMenu = runBeanMapByMenu.get(menuType.getBeanName());
        runByMenu.run();
    }

}
