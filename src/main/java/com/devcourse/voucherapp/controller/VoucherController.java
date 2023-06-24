package com.devcourse.voucherapp.controller;

import static com.devcourse.voucherapp.entity.Menu.getMenu;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.view.InputView;
import com.devcourse.voucherapp.view.OutputView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;

    @Autowired
    public VoucherController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while (isRunning) {
            outputView.showMenu();
            String menuNumber = inputView.inputWithTrimming();
            Menu menu = getMenu(menuNumber);

            isRunning = isNotQuit(menu);
        }

        outputView.showQuitMessage();
    }

    private boolean isNotQuit(Menu menu) {
        return menu != Menu.QUIT;
    }
}
