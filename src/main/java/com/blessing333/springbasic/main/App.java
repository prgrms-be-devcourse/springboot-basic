package com.blessing333.springbasic.main;

import com.blessing333.springbasic.RunnableController;
import com.blessing333.springbasic.common.ui.UserInterface;
import com.blessing333.springbasic.common.util.ExceptionStackTraceConverter;
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
    private final UserInterface userInterface = new MainAppInterface();
    private final ControllerMapper controllerMapper;

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
            RunnableController controller = controllerMapper.getRunnableController(strategy);
            controller.startService();
        }catch (NotSupportedStrategyException e){
            log.error(ExceptionStackTraceConverter.convertToString(e));
            userInterface.printMessage(e.getMessage());
        }
    }

}
