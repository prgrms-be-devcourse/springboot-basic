package org.prgrms.orderApp.CMDApplication.service;

import org.prgrms.orderApp.CMDApplication.console.Console;
import org.prgrms.orderApp.CMDApplication.console.script.AllScriptForConsole;
import org.prgrms.orderApp.monguDb.service.DbManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MonguDbCMDService  implements AllScriptForConsole{

    @Autowired
    private Console console;

    @Autowired
    DbManagement dbManagement;


    private String collectionName;

    public void createCollection() throws IOException {
        console.print(divisionLine);
        collectionName = console.input(monguDbCollectionCreate_GuideMessage);
        console.infoMessage( dbManagement.getConnection().DbConnection().createdCollection(collectionName));
        console.print(divisionBlank);

    }


    public void dropCollection() {
        console.print(divisionLine);
        collectionName = console.input(monguDbCollectionDrop_GuideMessage);
        console.infoMessage(dbManagement.getConnection().DbConnection().dropCollection(collectionName));
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
