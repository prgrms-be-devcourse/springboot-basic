package com.programmers.springbootbasic.util;


import static com.programmers.springbootbasic.exception.ErrorCode.FILE_IO_ERROR;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.programmers.springbootbasic.exception.exceptionClass.SystemException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CsvManager implements FileManager{

    private static final Set<String> EXTENSION = Set.of("csv");

    @Override
    public boolean supports(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return EXTENSION.contains(extension);
    }

    @Override
    public <T> List<T> read(String fileName, Class<T> type){
        Resource resource = new PathResource(fileName);

        if (!resource.exists()) {
            return new ArrayList<>();  // 파일이 없으면 빈 리스트 반환
        }

        try (Reader reader = new FileReader(resource.getFile())) {
            // CsvToBean 객체 생성 및 파싱
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            return csvToBean.parse();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new SystemException(FILE_IO_ERROR);
        }
    }

    @Override
    public <T> void write(T entity, String fileName) {
        try {
            Resource resource = new PathResource(fileName);
            File file = null;
            if (!resource.exists()) {
                resource.getFile().createNewFile();
            }
            file = resource.getFile();
            try (FileWriter writer = new FileWriter(file)) {
                StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(',')
                    .build();

                beanToCsv.write(entity);
            }
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new SystemException(FILE_IO_ERROR);
        }
    }



}
