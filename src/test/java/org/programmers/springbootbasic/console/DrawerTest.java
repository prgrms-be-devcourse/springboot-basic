package org.programmers.springbootbasic.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.VoucherType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DrawerTest {

    @Test
    @DisplayName("변환 출력 테스트")
    void draw() throws IOException {
        Model model = new Model();
        model.addAttributes("userName1", "test user");
        model.addAttributes("userName2", "programmer who reading this");
        ModelAndView modelAndView = new ModelAndView(model, "test-template.txt", ConsoleResponseCode.OK);
        Drawer drawer = new Drawer();
        drawer.draw(modelAndView);
    }
}