package org.prgms.io;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.prgms.customer.Customer;
import org.prgms.customer.Gender;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileReader implements FileReader {
    @Override
    public List<Customer> readFile(File file) throws IOException, CsvValidationException {
        List<Customer> users = new ArrayList<>();
        CSVReader reader = new CSVReader(new java.io.FileReader(file));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            Gender gender;
            if (nextLine[1].equals("남")) {
                gender = Gender.MALE;
            } else if (nextLine[1].equals("여")) {
                gender = Gender.FEMALE;
            } else {
                continue;
            }
            users.add(new Customer(nextLine[0], gender, Integer.parseInt(nextLine[2])));
        }
        return users;
    }
}
