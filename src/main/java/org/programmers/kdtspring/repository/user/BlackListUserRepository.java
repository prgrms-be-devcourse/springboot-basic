package org.programmers.kdtspring.repository.user;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.programmers.kdtspring.entity.user.User;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Repository
public class BlackListUserRepository implements BlackUserRepository {

    private static final String csvFile = "customer_blacklist.csv";

    @Override
    public void saveBlackUser(User user) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            String[] record = (user.getUserId() + "," + user.getUserName()).split(",");
            writer.writeNext(record);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String[]> findAll() {
        CSVReader reader = null;

        try {
            reader = new CSVReader(new FileReader(csvFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String[]> allBlackList = null;
        try {
            allBlackList = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return allBlackList;
    }
}