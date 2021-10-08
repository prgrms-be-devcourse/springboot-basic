package org.prgrms.orderApp.common;

/**
 * Created by yunyun on 2021/10/08.
 */
public enum DateFormat {
    SEARCH_DATE_FORMAT("yyyy-MM-dd HH:mm:ss.SSS");


    private String dateFormat;

    DateFormat(String dateFormat){
        this.dateFormat = dateFormat;
    }

    public String getDataFormat(){
        return this.dateFormat;
    }
}
