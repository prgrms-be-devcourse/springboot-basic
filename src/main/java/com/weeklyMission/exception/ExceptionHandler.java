package com.weeklyMission.exception;

import com.weeklyMission.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    private final Client client;

    public ExceptionHandler(Client client) {
        this.client = client;
    }

    public void run(){
        try{
            client.run();
        } catch(IncorrectInputException ne){
            System.out.println(ne.getMessage());
            logger.error("발생 지문 : " + ne.getQuest() + " 입력 받은 값 : " + ne.getInput());
        } catch(Exception e){
            System.out.println("서버 내부 오류입니다." + System.lineSeparator());
            logger.error("서버 내부 오류");
        }

    }
}
