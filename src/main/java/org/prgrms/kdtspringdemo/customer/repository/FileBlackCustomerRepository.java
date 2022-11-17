package org.prgrms.kdtspringdemo.customer.repository;

import org.springframework.context.annotation.Profile;

//@Repository
@Profile({"default", "dev"})
public class FileBlackCustomerRepository {
//    private final CSVReader csvReader;
//    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();
//    private final Logger logger = LoggerFactory.getLogger(FileBlackCustomerRepository.class);
//
//    public FileBlackCustomerRepository(@Value("${customer.path}") String path) {
//        csvReader = new CSVReader(path);
//
//    }
//
//    @PostConstruct
//    public void initStorage() {
//        try {
//            CsvDto csvDto = csvReader.readCSV();
//            List<Customer> customers = makeCustomersFromCsvLine(csvDto);
//            customers.forEach(customer ->
//                    storage.put(customer.getCustomerId(), customer));
//
//        } catch (FileNotFoundException e) {
//            logger.error("[black_list load fail] customer_blacklist.csv가 있는지 확인해 주세요");
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    private List<Customer> makeCustomersFromCsvLine(CsvDto csvDto) {
//        List<Customer> customers = new ArrayList<>();
//        for (String[] field : csvDto.value) {
//            UUID customerId = UUID.fromString(field[0]);
//            String name = field[1];
//            LocalDate birthDay = LocalDate.parse(field[2]);
//            String email = field[3];
//            LocalDateTime createdAt = LocalDateTime.parse(field[4]);
//            customers.add(new Customer(customerId, name, birthDay, email, createdAt, blackList));
//        }
//
//    }
//
//    public List<Customer> findAll() {
//        return new ArrayList<>(storage.values());
//    }
}
