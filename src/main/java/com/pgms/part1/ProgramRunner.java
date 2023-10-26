package com.pgms.part1;

import com.pgms.part1.domain.common.controller.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ProgramRunner.class);
    private final CommonController commonController;
    private Boolean isRunning = true;

    public ProgramRunner(CommonController commonController) {
        this.commonController = commonController;
    }

    @Override
    public void run(String... args) throws Exception {
        while(isRunning){
            try{
                commonController.getMenu();
            }
            catch (Exception e){
                log.warn(e.getMessage());
            }
        }
    }
}
