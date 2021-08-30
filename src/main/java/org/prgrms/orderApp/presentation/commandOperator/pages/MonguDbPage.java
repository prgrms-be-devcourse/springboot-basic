package org.prgrms.orderApp.presentation.commandOperator.pages;

import org.prgrms.orderApp.infrastructure.library.console.script.BasicScript;
import org.prgrms.orderApp.infrastructure.library.console.script.ForUxScript;
import org.prgrms.orderApp.presentation.commandOperator.script.ApplicationScript;
import org.prgrms.orderApp.presentation.commandOperator.script.MonguDbScript;
import org.prgrms.orderApp.presentation.commandOperator.script.WarnningAndErrorScript;
import org.prgrms.orderApp.service.MongoDbManageApplicationService;
import org.prgrms.orderApp.infrastructure.library.console.Console;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MonguDbPage {

    private Console console;
    private MongoDbManageApplicationService mongoDbManageApplicationService;

    private String collectionName;

    public MonguDbPage(Console console, MongoDbManageApplicationService mongoDbManageApplicationService){
        this.console = console;
        this.mongoDbManageApplicationService = mongoDbManageApplicationService;
    }
    public void createCollection() throws IOException {
        console.print(ForUxScript.DIVISION_LINE);
        collectionName = console.input(MonguDbScript.MONGUDB_COLLECTION_CREATE__GUIDE_MESSAGE);
        console.infoMessage(mongoDbManageApplicationService.createCollection(collectionName) );
        console.print(ForUxScript.DIVISION_BLANK);

    }


    public void dropCollection() {
        console.print(ForUxScript.DIVISION_LINE);
        collectionName = console.input(MonguDbScript.MONGUDB_COLLECTION_DROP__GUIDE_MESSAGE);
        console.infoMessage(mongoDbManageApplicationService.dropCollection(collectionName));
        console.print(ForUxScript.DIVISION_BLANK);

    }

    public void connectCollection(String collectionName) {

    }

    public String selectedMonguMainMenu() throws IOException {
        console.print(ForUxScript.DIVISION_LINE);
        console.infoMessage(MonguDbScript.MONGUDB_MAIN_MENU);
        return console.input(BasicScript.INPUT_USER_SELECTED_MENU_NUMBER);

    }
}
