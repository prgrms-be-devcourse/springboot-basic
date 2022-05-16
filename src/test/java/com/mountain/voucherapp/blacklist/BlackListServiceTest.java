package com.mountain.voucherapp.blacklist;

import com.mountain.voucherapp.dao.blackList.BlackListFileFormat;
import com.mountain.voucherapp.dao.blackList.DefaultBlackListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.util.List;

class BlackListServiceTest {

    DefaultBlackListRepository blackListRepository = new DefaultBlackListRepository();

    @Description("csv파일_읽기_테스트")
    @Test
    public void readCsvFileTest() throws Exception {
        //given
        blackListRepository.postConstruct();
        //when
        List<BlackListFileFormat> list = blackListRepository.getBlackList();
        //then
        list.stream()
                .forEach(System.out::println);

        Assertions.assertEquals(6, list.size());
        Assertions.assertEquals("UUID1", list.get(0).getId());
    }

}