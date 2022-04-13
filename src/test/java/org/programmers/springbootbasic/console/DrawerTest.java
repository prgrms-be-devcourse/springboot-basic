package org.programmers.springbootbasic.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DrawerTest {

    @Test
    @DisplayName("변환 출력 테스트")
    void draw() throws IOException {
        Model model = new Model();
        model.addAttributes("userName1", "test user");
        model.addAttributes("userName2", "programmer who reading this");
        ModelAndView modelAndView = new ModelAndView(model, "test-template.txt", ConsoleResponseCode.PROCEED);
        Drawer drawer = new Drawer();
        drawer.draw(modelAndView);
    }
}