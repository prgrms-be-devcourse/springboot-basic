package prgms.springbasic.BlackCustomer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("customerBlacklistRepository")
public class BlackCustomerRepositoryImpl implements BlackCustomerRepository {

    private static final String path = "src/main/customer_blacklist.csv";
    private static final int NAME = 0;
    private static final int CUSTOMER_ID = 1;

    private final File file;
    private final BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public BlackCustomerRepositoryImpl() throws IOException {
        this.file = new File(path);

        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public BlackCustomer save(BlackCustomer customer) {
        try {
            bufferedWriter.write(customer.toString() + System.lineSeparator());
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("레포지토리에 고객을 저장할 수 없습니다.");
        }
        return customer;
    }

    @Override
    public Optional<BlackCustomer> findByName(String name) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] splited = line.split(", ");
                if (splited[NAME].equals(name)) {
                    return Optional.of(new BlackCustomer(splited[NAME], UUID.fromString(splited[CUSTOMER_ID])));
                }
            }

            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException("레포지토리를 읽을 수 없습니다.");
        }
    }

    @Override
    public List<BlackCustomer> getCustomerList() {
        String line;
        List<BlackCustomer> blackList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                String[] splited = line.split(", ");
                blackList.add(new BlackCustomer(splited[0], UUID.fromString(splited[1])));
            }
        } catch (IOException e) {
            throw new RuntimeException("레포지토리를 읽을 수 없습니다.");
        }
        return blackList;
    }
}
