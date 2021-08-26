package org.prgrms.orderApp.infrastructure.library.monguDb.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.infrastructure.library.monguDb.util.DbWriter;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;

public class DbDocument {

    private static String storagePath;

    public DbDocument(String storagePath) {
        DbDocument.storagePath = storagePath;
    }

    public DbWriter insertOne(JSONObject contents) throws IOException, ParseException {
        return new DbWriter(storagePath, contents);
    }

    public JSONArray find() throws IOException, ParseException {

        //return (JSONArray) new JSONParser().parse(new FileReader(storagePath+ ".json"));
        return (JSONArray) new JSONParser().parse(new FileReader(ResourceUtils.getFile(storagePath+ ".json").getAbsolutePath()));
    }
}
