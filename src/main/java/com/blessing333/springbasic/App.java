package com.blessing333.springbasic;

import com.blessing333.springbasic.exception.NotSupportedStrategyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class App implements ApplicationRunner {
    private static final String PROGRAM_EXIT = "exit";
    private final UserInterface userInterface = new AppInterface();
    private final ServiceProvider provider;

    @Override
    public void run(ApplicationArguments args){
        while(true){
            userInterface.showGuideText();
            String command = userInterface.inputMessage();
            if(PROGRAM_EXIT.equals(command)) break;
            else doService(command);
        }
    }

    private void doService(String command){
        try{
            ServiceStrategy strategy = ServiceStrategy.fromString(command);
            RunnableService service = provider.getRunnableService(strategy);
            service.startService();
        }catch (NotSupportedStrategyException e){
            log.error(e.getMessage());
        }
    }
}
