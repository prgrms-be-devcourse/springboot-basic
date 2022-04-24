package com.mountain.voucherApp.adapter.out.file;

import com.opencsv.bean.CsvBindByPosition;

public class BlackListFileFormat {

    @CsvBindByPosition(position = 0)
    private String id;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                '}';
    }
}
