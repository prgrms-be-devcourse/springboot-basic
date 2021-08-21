package org.prgrms.orderApp.monguDb.service;

import org.json.simple.JSONObject;
import org.prgrms.orderApp.monguDb.DbWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

public class DbCommand {

    private final String storagePath;


    public DbCommand(String storagePath) {
        this.storagePath = storagePath;
    }

    DbWriter insertOne(JSONObject contents) throws IOException {
        return new DbWriter(storagePath, contents);
    }

    JSONObject find(){
        return new JSONObject();
    }
}
