package org.prgrms.kdt.kdtspringorder.common.io;

import com.opencsv.CSVReader;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("file-scv-io")
public class FileScvIo implements FileIo<Customer>{

    @Value(value = "${kdt.dev.file-io.cvs-path}")
    private String CVS_PATH;

    @Override
    public void write(List<Customer> oList) {

    }

    @Override
    public List<Customer> readAllLines() {

        List<Customer> blackCustomerList = new ArrayList<>();

        CSVReader reader = null;

        try {

            reader = new CSVReader(new FileReader(CVS_PATH));

            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Customer blackCustomer = new Customer(nextLine[0], nextLine[1]);
                blackCustomerList.add(blackCustomer);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return blackCustomerList;

    }


}
