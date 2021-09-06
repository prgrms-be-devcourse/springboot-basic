package org.prgrms.orderApp.commandOperator.service;

import org.prgrms.orderApp.util.library.monguDb.DbManagement;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MongoDbManageApplicationService {
    private DbManagement dbManagement;

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
