package com.weeklyMission.exception;

import com.weeklyMission.client.Client;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler extends Client{

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    private final Client client;

    public ExceptionHandler(Client client) {
        super(null,null,null,null);
        this.client = client;
    }

    public void run(){
        try{
            client.run();
        } catch(IncorrectInputException ne){
            System.out.println(ne.getMessage());
            logger.error("발생 지문 : {} , 입력 받은 값 : {}", ne.getQuest(), ne.getInput());
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
            logger.error(ie.getMessage());
        } catch(NoSuchElementException noe){
            System.out.println(noe.getMessage());
            logger.error(noe.getMessage());
        }
//        catch(Exception e){
//            System.out.println("서버 내부 오류입니다." + System.lineSeparator());
//            logger.error("서버 내부 오류");
//        }
    }
}
