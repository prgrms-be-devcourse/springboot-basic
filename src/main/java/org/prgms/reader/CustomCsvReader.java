package org.prgms.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.prgms.user.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomCsvReader implements Reader {
    @Override
    public List<User> readFile(File file) throws IOException, CsvValidationException {
        List<User> users = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            users.add(new User(nextLine[0], nextLine[1], Integer.parseInt(nextLine[2])));
        }
        return users;
    }
}
