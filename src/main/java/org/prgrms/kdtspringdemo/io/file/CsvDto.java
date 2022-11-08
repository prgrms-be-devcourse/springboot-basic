package org.prgrms.kdtspringdemo.io.file;

import java.util.List;

public class CsvDto {
    public final List<String[]> value;

    private CsvDto(List<String[]> value) {
        this.value = value;
    }
    public static CsvDto from(List<String[]> value){
        return new CsvDto(value);
    }

}
