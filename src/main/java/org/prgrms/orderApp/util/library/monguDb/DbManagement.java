package org.prgrms.orderApp.util.library.monguDb;

import org.springframework.stereotype.Component;

@Component
public class DbManagement {
    private DbConnection dbConnection;

    public DbConnection getConnection(){
        if (dbConnection == null){
            this.dbConnection = new DbConnection();
        }
        return dbConnection;
    }
}
