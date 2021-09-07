package prgms.springbasic.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Qualifier("customerBlacklistRepository")
public class CustomerBlacklistRepository implements CustomerRepository {

    private static final String path = "src/main/customer_blacklist.csv";
    private final File file;
    private final BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public CustomerBlacklistRepository() throws IOException {
        this.file = new File(path);

        if (!file.exists()) {
            file.createNewFile();
        }
        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public Customer save(Customer customer) {
        try {
            bufferedWriter.write(customer.toString() + '\n');
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer findByName(String name) {
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                String[] splited = line.split(", ");
                if (splited[0].equals(name)) {
                    return new Customer(splited[0], UUID.fromString(splited[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getCustomerList() {
        String line;
        List<Customer> blackList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                String[] splited = line.split(", ");
                blackList.add(new Customer(splited[0], UUID.fromString(splited[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackList;
    }
}
