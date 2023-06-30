package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.utils.PathProperties;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class FileCustomerRepository implements CustomerRepository {

    private final String BLACKLIST_FILE_PATH;

    public FileCustomerRepository(PathProperties pathProperties) {
        this.BLACKLIST_FILE_PATH = pathProperties.getBlacklist();
    }

    @Override
    public List<List<String>> findAllBlackList() {
        List<List<String>> blackList = new ArrayList<List<String>>();
        File csv = new File(BLACKLIST_FILE_PATH);
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                List<String> aLine = new ArrayList<String>();
                String[] lineArr = line.split(",");
                aLine = Arrays.asList(lineArr);
                blackList.add(aLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return blackList;

    }
}
