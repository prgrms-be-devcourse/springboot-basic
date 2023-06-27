package kr.co.programmers.springbootbasic.customer.impl;

import kr.co.programmers.springbootbasic.customer.Customer;
import kr.co.programmers.springbootbasic.customer.CustomerRepository;
import kr.co.programmers.springbootbasic.customer.CustomerStatus;
import kr.co.programmers.springbootbasic.exception.FileConvertFailException;
import kr.co.programmers.springbootbasic.exception.FileRepositoryInitException;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CsvCustomerRepository.class);
    private static final String USER_BLACK_LIST_PATH = "src/main/resources/customer_blacklist.csv";
    private static final int CUSTOMER_ID_INDEX = 0;
    private static final int CUSTOMER_NAME_INDEX = 1;
    private static final int CUSTOMER_STATUS_INDEX = 2;

    public CsvCustomerRepository() throws RuntimeException {
        logger.info("CsvCustomerRepository를 초기화합니다.");
        createBlackListFileAfterExistCheck();
    }

    @Override
    public List<Customer> listAllBlackCustomer() {
        File file = new File(USER_BLACK_LIST_PATH);
        List<Customer> blackList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            logger.info("블랙리스트들을 Customer 객체로 만듭니다...");
            blackList = createBlackCustomers(bufferedReader);
        } catch (IOException e) {
            logger.error("블랙리스트를 Customer 객체로 만드는데 실패했습니다.");
            throw new FileConvertFailException("블랙리스트를 가져오는 도중 오류가 발생했습니다.");
        }
        logger.info("블랙리스트를 Customer 객체로 만드는데 성공했습니다.");

        return blackList;
    }

    private void createBlackListFileAfterExistCheck() {
        logger.info("블랙리스트 CSV 파일이 경로 있는지 체크합니다...");
        Path path = Paths.get(USER_BLACK_LIST_PATH);
        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            logger.info("유저 블랙리스트 CSV 파일이 존재하므로 새로 생성하지 않습니다.");
            return;
        }
        logger.info("블랙리스트 CSV 파일이 존재하지 않으므로 새로 생성합니다.");
        createBlackListFile(path);
    }

    private void createBlackListFile(Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            logger.error("블랙리스트 CSV 파일시스템에 오류가 발생했습니다.");
            throw new FileRepositoryInitException("블랙리스트 CSV 파일시스템에 오류가 발생했습니다.\n");
        }
    }

    private List<Customer> createBlackCustomers(BufferedReader bufferedReader) throws IOException {
        String line;
        List<Customer> blackList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            String[] customerProperties = line.split(",");
            long id = ApplicationUtils.parseStringToLong(customerProperties[CUSTOMER_ID_INDEX]);
            String name = customerProperties[CUSTOMER_NAME_INDEX];
            CustomerStatus status = CustomerStatus.valueOf(customerProperties[CUSTOMER_STATUS_INDEX]);
            blackList.add(new CsvCustomer(id, name, status));
        }

        return blackList;
    }
}
