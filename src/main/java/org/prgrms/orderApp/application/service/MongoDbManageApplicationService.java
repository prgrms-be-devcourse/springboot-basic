package org.prgrms.orderApp.application.service;

import org.prgrms.orderApp.infrastructure.library.monguDb.service.DbManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MongoDbManageApplicationService {
    @Autowired
    DbManagement dbManagement;


    public String dropCollection(String collectionName) throws IOException {
        return dbManagement.getConnection().DbConnection().createdCollection(collectionName);
    }
    public String createCollection(String collectionName){
        return dbManagement.getConnection().DbConnection().dropCollection(collectionName);
    }
}
