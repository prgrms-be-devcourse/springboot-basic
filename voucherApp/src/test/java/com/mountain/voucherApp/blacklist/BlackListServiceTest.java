package com.mountain.voucherApp.blacklist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlackListServiceTest {

    BlackListService blackListService = new BlackListService();

    @Description("csv파일_읽기_테스트")
    @Test
    public void readCsvFileTest() throws Exception {
        //given
        blackListService.postConstruct();
        //when
        List<BlackListFileFormat> list = blackListService.readCSVFile();
        //then
        list.stream()
                .forEach(System.out::println);

        Assertions.assertEquals(6, list.size());
        Assertions.assertEquals("UUID1", list.get(0).getId());
    }

}