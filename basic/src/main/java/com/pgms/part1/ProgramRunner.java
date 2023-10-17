package com.pgms.part1;

import com.pgms.part1.domain.view.InitConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {
    private InitConsole initConsole;

    public ProgramRunner(InitConsole initConsole) {
        this.initConsole = initConsole;
    }

    @Override
    public void run(String... args) throws Exception {
        initConsole.init();
    }
}
