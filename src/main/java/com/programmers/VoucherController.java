package com.programmers;

import com.programmers.io.Console;
import org.springframework.stereotype.Controller;

import java.awt.*;

@Controller
public class VoucherController {

    private final Console console = new Console();

    public void run() {
        console.printMenu();
    }
}
