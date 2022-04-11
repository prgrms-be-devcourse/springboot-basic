package com.example.voucherproject.common.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.example.voucherproject.VoucherProjectApplication.applicationContext;

@Slf4j
@Component
public class CSVReader{

    public List<List<String>> readUser(String fileName) {
        var blackList = new ArrayList<List<String>>(); // TODO : atomic?


        var resource = applicationContext.getResource(fileName); //TODO : public 접근 지양
        // TODO : 실행시점에 한번만 읽혀지는듯?? 계속 반영되게 못하나?

        try (var blacks = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            log.info("Reading black list start ....");
            blacks.lines().forEach(black ->{
                blackList.add(Arrays.asList(black.split(",")));
            });
            log.info("Reading complete");
        } catch (Exception e) {
            log.error("파일 읽기 오류 "+e.getMessage()); // TODO: 에러로그 저장
        }
        return blackList;
    }
}
