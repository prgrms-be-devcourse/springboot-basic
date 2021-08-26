package org.prgrms.orderApp.infrastructure.library.monguDb.service;

import java.io.FileReader;
import java.io.FileWriter;



public class DbConnection {

    private final String db = "src/main/resources/storage";
    private DbCollection dbCollection;
    private FileWriter writer ;
    private FileReader reader ;

    public DbCollection DbConnection(){
        if (dbCollection == null){
            this.dbCollection = new DbCollection(db);
        }
        return dbCollection;
    }

}

