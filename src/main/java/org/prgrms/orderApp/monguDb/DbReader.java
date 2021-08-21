package org.prgrms.orderApp.monguDb;

import java.io.FileReader;
import java.io.IOException;


public class DbReader {

    private final FileReader fileReader;

    public DbReader(String storagePath) throws IOException {
        fileReader = new FileReader(storagePath+ ".json");
        //file.write(obj.toJSONString());

    }

    void close() throws IOException {
        fileReader.close();
    }
}
