package org.prgrms.orderApp.monguDb.service;

import java.io.File;
import java.io.IOException;

public class DbCollection {
    private String db ;
    private int flag;
    private  File file;

    public DbCollection() {
    }

    public DbCollection(String db) {
        this.db = db;
    }


    public Object getCollection(String collection) {
        return new DbDocument(db+"/"+collection);
    }

    public String createdCollection(String collection) throws IOException {
        file = new File(db+"/"+collection+".json");
        if(file.createNewFile()){
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
