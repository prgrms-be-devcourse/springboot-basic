package org.prgrms.kdt.kdtspringorder.common.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TextFileIoTest {

    private FileIo fileIo;
    private List<String> strList;

    @BeforeEach
    void setUp(){
        fileIo = new TextFileIo();
        strList = new ArrayList<>();
        strList.add("가,asd");
        strList.add("나,dfs");
        strList.add("다,fhgg");
        strList.add("라,ghj");

    }

    @Test
    void write() {
        try {
            fileIo.write(strList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readAllLines() {
        try {
            List<String> strings = fileIo.readAllLines();
            strings.forEach(s -> System.out.println(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
