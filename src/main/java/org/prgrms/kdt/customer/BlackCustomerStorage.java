package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BlackCustomerStorage {

    private static final String FILE_PATH = "black_list_customer.csv";
    private static final File blackListFile = new File(FILE_PATH);
    private static final Logger logger = LoggerFactory.getLogger(BlackCustomerStorage.class);

    public List<String> findAll(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(blackListFile));
            return bufferedReader.lines()
                    .toList();
        } catch (FileNotFoundException e){
            logger.error("파일을 읽어올 수 없습니다.");
            return new ArrayList<>();
        }
    }
}
