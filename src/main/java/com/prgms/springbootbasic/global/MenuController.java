package com.prgms.springbootbasic.global;

import com.prgms.springbootbasic.member.controller.MemberController;
import com.prgms.springbootbasic.global.ui.Console;
import com.prgms.springbootbasic.member.model.BlackList;
import com.prgms.springbootbasic.member.util.MemberMenu;
import com.prgms.springbootbasic.global.util.Menu;
import com.prgms.springbootbasic.voucher.controller.VoucherController;
import com.prgms.springbootbasic.voucher.util.VoucherMenu;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class MenuController {

    private final Console console;
    public MenuController(Console console) {
        this.console = console;
    }

    public boolean run() {
        try {
            String command = console.init();
            Menu menu = Menu.of(command);
            return runExit(menu);
        } catch (Exception e) {
            console.showExceptionMessage(e.getMessage());
        }
        return true;
    }

    private boolean runExit(Menu menu) throws IOException {
        if (menu == Menu.EXIT) {
            console.exit();
            return false;
        }
        return runApplication(menu);
    }

    private boolean runApplication(Menu menu) throws IOException {
        String command = console.initApplication(menu);
        if (menu == Menu.MEMBER) {
            MemberMenu memberMenu = MemberMenu.of(command);
            MemberController memberController = memberMenu.getMemberController();
            return memberController.run();
        }
        VoucherMenu voucherMenu = VoucherMenu.of(command);
        VoucherController voucherController = voucherMenu.getCommandLineController();
        return voucherController.run();
    }

}
