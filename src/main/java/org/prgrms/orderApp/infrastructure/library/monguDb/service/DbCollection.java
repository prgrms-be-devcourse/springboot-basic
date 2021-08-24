package org.prgrms.orderApp.infrastructure.library.monguDb.service;

import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DbCollection {
    private String db, filePath;
    private int flag;
    private  File file;
    private DbDocument dbDocument;
    private FileWriter fileWriter;
    private final JSONArray defaultData = new JSONArray();

    public DbCollection() {
    }

    public DbCollection(String db) {
        this.db = db;
    }


    public DbDocument getCollection(String collection) {
        dbDocument = new DbDocument(db+"/"+collection);
        return dbDocument;
    }

    public String createdCollection(String collection) throws IOException {
        defaultData.clear();
        filePath = db+"/"+collection+".json";
        file = new File(filePath);
        if(file.createNewFile()){
            fileWriter = new FileWriter(filePath);
            try{
                fileWriter.write(String.valueOf(defaultData));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                fileWriter.close();
            }
            return "컬렉션이 생성되었습니다.";
        }else{
            return "이미 존재한 컬렉션입니다. 확인 바랍니다.";
        }
    }

    public String dropCollection(String collection){
        file = new File(db+"/"+collection+".json");
        if(file.exists()){
            if(file.delete()){
                return "삭제 완료하였습니다.";
            }else {
                return "삭제 실패하였습니다.";
            }
        }else {
            return "본 컬랙션은 존재하지 않습니다.";
        }
    }

}
