package org.prgrms.springorder.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.BlockCustomer;
import org.springframework.test.context.ActiveProfiles;


@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("file")
class FileBlockCustomerRepositoryTest {

    private FileBlockCustomerRepository fileBlockCustomerRepository;

    private String path = "test/store/";
    private String fileName = "customer_blacklist";
    private String fileExtension = ".csv";

    @BeforeAll
    public void init() throws IOException {
        fileBlockCustomerRepository = new FileBlockCustomerRepository(path, fileName,
            fileExtension);
    }

    @BeforeEach
    void after() {
        fileBlockCustomerRepository.deleteAll();
    }

    @DisplayName("file 생성 테스트 - 레포지토리가 생성되면 파일은 생성되어 있다.")
    @Test
    void fileCreateTest() {
        // given
        String filePath = path + fileName + fileExtension;
        File file = new File(filePath);

        //when
        boolean exists = file.exists();

        //then
        assertTrue(exists);
    }

    @DisplayName("insert 테스트 - file에 정상 저장되고 저장된 BlockCustomer 를 리턴한다.")
    @Test
    void insertTest() throws IOException {
        //given
        UUID blockId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        BlockCustomer blockCustomer = new BlockCustomer(blockId, customerId, LocalDateTime.now());

        //when
        BlockCustomer savedBlockCustomer = fileBlockCustomerRepository.insert(blockCustomer);

        //then
        assertNotNull(savedBlockCustomer);
        assertEquals(blockCustomer, savedBlockCustomer);

        List<BlockCustomer> findBlockCustomerInFile = readAll(
            new File(path + fileName + fileExtension));
        assertTrue(findBlockCustomerInFile.contains(savedBlockCustomer));
    }

    @DisplayName("findAll 테스트 - 저장된 BlockCustomer 가 모두 리턴된다.")
    @Test
    void findAllTest() {
        //given

        int saveCount = 5;
        IntStream.range(0, saveCount).forEach(i -> {
            UUID blockId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();

            BlockCustomer blockCustomer = new BlockCustomer(blockId, customerId,
                LocalDateTime.now());

            fileBlockCustomerRepository.insert(blockCustomer);
        });

        //when
        List<BlockCustomer> blockCustomers = fileBlockCustomerRepository.findAll();
        //then
        assertNotNull(blockCustomers);
        assertEquals(saveCount, blockCustomers.size());
    }

    @DisplayName("findAll 테스트 - 저장된 BlockCustomer 가 없다면 빈 리스트가 반환된다.")
    @Test
    void findAllReturnEmptyListTest() {
        //given & when
        List<BlockCustomer> blockCustomers = fileBlockCustomerRepository.findAll();
        //then
        assertNotNull(blockCustomers);
        assertTrue(blockCustomers.isEmpty());
    }

    private List<BlockCustomer> readAll(File file) throws IOException {

        List<BlockCustomer> blockCustomers = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            String line = "";
            while (((line = bufferedReader.readLine()) != null)) {
                String[] split = line.split(",");

                UUID blockId = UUID.fromString(split[0].trim());
                UUID customerId = UUID.fromString(split[1].trim());
                LocalDateTime registeredAt = LocalDateTime.parse(split[2].trim(), formatter);

                BlockCustomer blockCustomer = new BlockCustomer(blockId, customerId, registeredAt);

                blockCustomers.add(blockCustomer);
            }

        }

        return blockCustomers;
    }

}