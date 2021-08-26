package org.prgrms.orderApp.infrastructure.library.monguDb.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class DbWriter {
    private final FileWriter fileWriter;
    private FileReader fileReader;
    private JSONArray dataBeforeInsert;
    private String filePath;

    public DbWriter(String storagePath, JSONObject contents) throws IOException, ParseException {

        filePath = storagePath+ ".json";
        fileReader = new FileReader(filePath);

        try{
            dataBeforeInsert = (JSONArray) new JSONParser().parse(fileReader);
        }catch (Exception e){
            e.printStackTrace();
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
