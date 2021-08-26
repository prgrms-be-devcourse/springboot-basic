package org.prgrms.orderApp.infrastructure.library.monguDb.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.OrderAppApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class DbWriter {
    private final static Logger logger = LoggerFactory.getLogger(DbWriter.class);

    private final FileWriter fileWriter;
    private FileReader fileReader;
    private JSONArray dataBeforeInsert;
    private String filePath;

    public DbWriter(String storagePath, JSONObject contents) throws IOException, ParseException {

        filePath = ResourceUtils.getFile(storagePath+ ".json").getAbsolutePath();

        fileReader = new FileReader(filePath);

        try{
            dataBeforeInsert = (JSONArray) new JSONParser().parse(fileReader);
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            fileReader.close();
        }
        dataBeforeInsert.add(contents);

        fileWriter = new FileWriter(filePath);
        fileWriter.write(String.valueOf(dataBeforeInsert));

    }
    public void flush() throws IOException {
        fileWriter.flush();
    }

    public void close() throws IOException {
        fileWriter.close();
    }

}
