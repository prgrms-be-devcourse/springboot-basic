package com.mountain.voucherApp.blacklist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlackListServiceTest {

    BlackListService blackListService = new BlackListService();

    @Description("csv파일_읽기_테스트_콘솔출력_확인")
    @Test
    public void readCsvFileTest() throws Exception {
        //given
        blackListService.postConstruct();
        //when
        List<List<String>> lists = blackListService.readCSVFile();
        //then
        lists.stream()
                .forEach(System.out::println);
    }

}