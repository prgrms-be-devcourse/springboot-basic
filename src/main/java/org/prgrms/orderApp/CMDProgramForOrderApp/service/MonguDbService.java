package org.prgrms.orderApp.CMDProgramForOrderApp.service;

import java.io.IOException;

public interface MonguDbService {

    public void createCollection() throws IOException;
    public void dropCollection();
    public void connectCollection(String collectionName);
    public String selectedMonguMainMenu() throws IOException;
}
