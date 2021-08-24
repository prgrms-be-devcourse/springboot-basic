package com.org.prgrms.mission;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.model.Customer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Input {

    public <T> T insert(T t, String filePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<T>(fileWriter).build();
        beanToCsv.write(List.of(t));
        fileWriter.close();
        return t;
    }
}
