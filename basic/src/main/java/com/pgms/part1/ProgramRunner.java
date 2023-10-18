package com.pgms.part1;

import com.pgms.part1.domain.view.ConsoleView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {
    private ConsoleView consoleView;

    public ProgramRunner(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    @Override
    public void run(String... args) throws Exception {
        consoleView.init();
    }
}
