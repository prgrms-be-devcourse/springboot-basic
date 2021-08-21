package org.prgrms.orderApp.monguDb.service;


import org.springframework.stereotype.Service;

@Service
public class DbManagement {

    private DbConnection dbConnection;


    public DbConnection getConnection(){
        if (dbConnection == null){
            this.dbConnection = new DbConnection();
        }
        return dbConnection;
    }

}
