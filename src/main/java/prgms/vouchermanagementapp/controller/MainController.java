package prgms.vouchermanagementapp.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("release")
public class MainController implements CommandLineRunner {

    private final CommandExecutor commandExecutor;

    public MainController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run(String... args) throws Exception {
        commandExecutor.run(new RunningState());
    }
}
