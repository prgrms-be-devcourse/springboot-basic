package org.prgrms.orderApp.presentation.commandOperator.pages;

import org.prgrms.orderApp.application.service.MongoDbManageApplicationService;
import org.prgrms.orderApp.application.service.OrderApplicationService;
import org.prgrms.orderApp.presentation.commandOperator.script.AllScriptForCMDApplication;
import org.prgrms.orderApp.infrastructure.library.console.Console;
import org.prgrms.orderApp.infrastructure.library.monguDb.service.DbManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MonguDbPage implements AllScriptForCMDApplication {

    private Console console;
    private MongoDbManageApplicationService mongoDbManageApplicationService;

    private String collectionName;

    public MonguDbPage(Console console, MongoDbManageApplicationService mongoDbManageApplicationService){
        this.console = console;
        this.mongoDbManageApplicationService = mongoDbManageApplicationService;
    }
    public void createCollection() throws IOException {
        console.print(divisionLine);
        collectionName = console.input(monguDbCollectionCreate_GuideMessage);
        console.infoMessage(mongoDbManageApplicationService.createCollection(collectionName) );
        console.print(divisionBlank);

    }


    public void dropCollection() {
        console.print(divisionLine);
        collectionName = console.input(monguDbCollectionDrop_GuideMessage);
        console.infoMessage(mongoDbManageApplicationService.dropCollection(collectionName));
        console.print(divisionBlank);

    }

    public void connectCollection(String collectionName) {

    }

    public String selectedMonguMainMenu() throws IOException {
        console.print(divisionLine);
        console.infoMessage(monguDbMainMenu);
        return console.input(inputUserSelectedMenuNumber);

    }
}
