package com.prgrms.vouchermanagement.infra;

import com.prgrms.vouchermanagement.infra.exception.MenuTypeFormatException;
import com.prgrms.vouchermanagement.infra.input.InputProvider;
import com.prgrms.vouchermanagement.infra.output.OutputProvider;
import com.prgrms.vouchermanagement.infra.utils.MenuPatternUtils;
import com.prgrms.vouchermanagement.infra.utils.MenuType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IOManager implements CommandLineRunner {

    private final InputProvider inputProvider;
    private final OutputProvider outputProvider;

    public IOManager(InputProvider inputProvider, OutputProvider outputProvider) {
        this.inputProvider = inputProvider;
        this.outputProvider = outputProvider;
    }

    public MenuType selectMenu() throws IOException {
        outputProvider.showMenu();
        String menuTypeString = inputProvider.getMenuType();
        if (!MenuPatternUtils.MENU.matcher(menuTypeString).matches()) {
            throw new MenuTypeFormatException();
        }
        return MenuType.getMenuType(menuTypeString);
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;

        while (isRunning) {

            MenuType menuType = selectMenu();
            switch(menuType) {
                case LIST:
                    System.out.println("==list==");
                    break;
                case CREATE:
                    System.out.println("==create==");
                    break;
                case EXIT:
                    System.out.println("==exit==");
                    isRunning = false;
                    break;
            }
        }
    }
}
