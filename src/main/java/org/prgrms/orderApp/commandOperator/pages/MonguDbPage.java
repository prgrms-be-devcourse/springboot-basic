package org.prgrms.orderApp.commandOperator.pages;

import org.prgrms.orderApp.commandOperator.script.MonguDbScript;
import org.prgrms.orderApp.commandOperator.service.MongoDbManageApplicationService;
import org.prgrms.orderApp.util.library.console.Console;
import org.prgrms.orderApp.util.library.console.script.BasicScript;
import org.prgrms.orderApp.util.library.console.script.ForUxScript;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
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
