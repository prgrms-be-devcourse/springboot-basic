package org.prgrms.orderApp.application.service;

import org.prgrms.orderApp.infrastructure.library.monguDb.service.DbManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MongoDbManageApplicationService {

    DbManagement dbManagement;

    public MongoDbManageApplicationService(DbManagement dbManagement){
        this.dbManagement = dbManagement;
    }

    public String dropCollection(String collectionName) {
        return dbManagement.getConnection().DbConnection().dropCollection(collectionName);
    }
    public String createCollection(String collectionName) throws IOException {
        return dbManagement.getConnection().DbConnection().createdCollection(collectionName);
    }
}
