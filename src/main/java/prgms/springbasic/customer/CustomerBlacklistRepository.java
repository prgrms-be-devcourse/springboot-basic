package prgms.springbasic.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
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

        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public Customer save(Customer customer) {
        try {
            bufferedWriter.write(customer.toString() + System.lineSeparator());
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("레포지토리에 고객을 저장할 수 없습니다.");
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
            throw new RuntimeException("레포지토리를 읽을 수 없습니다.");
        }
        throw new RuntimeException(MessageFormat.format("해당 이름의 고객을 찾을 수 없습니다. name -> {0}", name));
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
            throw new RuntimeException("레포지토리를 읽을 수 없습니다.");
        }
        return blackList;
    }
}
