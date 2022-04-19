package com.mountain.voucherApp.blacklist;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class BlackListService {

    private static final String FILE_PATH = "blackList/customer_blacklist.csv";
    private static final File BLACK_LIST_FILE = new FileSystemResource(FILE_PATH).getFile();
    private static final Logger log = LoggerFactory.getLogger(BlackListService.class);

    public List<BlackListFileFormat> readCSVFile() throws IOException {
        List<BlackListFileFormat> blackList = new CsvToBeanBuilder(new FileReader(FILE_PATH))
                .withType(BlackListFileFormat.class)
                .build()
                .parse();
        return blackList;
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        if (!BLACK_LIST_FILE.exists()) {
            BLACK_LIST_FILE.getParentFile().mkdir();
            BLACK_LIST_FILE.createNewFile();
            log.info("create new file");
        }
    }

}
