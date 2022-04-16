package com.example.voucherproject.common.io.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CSVReader implements MyReader{

    public List<List<String>> readLists(String fileName) {
        var ObjectList = new ArrayList<List<String>>();
        String absolute = "file:src/main/resources/"+fileName;

        var resource = new DefaultResourceLoader().getResource(absolute);
        try (var lines = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            lines.lines().forEach(line ->{
                ObjectList.add(Arrays.asList(line.split(",")));
            });
        } catch (Exception e) {
            log.error("파일 읽기 오류 "+e.getMessage());
        }
        return ObjectList;
    }
}
