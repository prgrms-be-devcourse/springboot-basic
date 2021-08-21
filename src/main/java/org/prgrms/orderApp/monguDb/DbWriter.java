package org.prgrms.orderApp.monguDb;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;


public class DbWriter {
    private final FileWriter fileWriter;

    public DbWriter(String storagePath, JSONObject contents) throws IOException {
        fileWriter = new FileWriter(storagePath+ ".json");
        fileWriter.write(contents.toJSONString());

    }
    void flush() throws IOException {
        fileWriter.flush();
    }

    void close() throws IOException {
        fileWriter.close();
    }

}
