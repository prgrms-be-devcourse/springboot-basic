package org.prgrms.vouchermanagement.voucher.repository;

import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

//    @Autowired
//    JdbcVoucherRepository jdbcVoucherRepository;
//    @Autowired
//    DataSource dataSource;
//    @Autowired
//    NamedParameterJdbcTemplate jdbcTemplate;
//    EmbeddedMysql embeddedMysql;
//
//    @BeforeAll
//    void setUp() {
//        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
//                .withCharset(UTF8)
//                .withPort(2215)
//                .withUser("test", "test")
//                .withTimeZone("Asia/Seoul")
//                .build();
//
//        embeddedMysql = anEmbeddedMysql(mysqldConfig)
//                .addSchema("test-customer", ScriptResolver.classPathScript("create_vouchers_schema.sql"))
//                .start();
//    }
//
//    @AfterAll
//    void cleanUp() {
//        embeddedMysql.stop();
//    }
//
//    @AfterEach
//    void clear() {
//        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
//    }
//
//    @Test
//    @DisplayName("바우처 저장 성공")
//    void saveVoucher() {
//        // given
//        Voucher fixedAmountVoucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100, "customerId");
//
//        // when
//        jdbcVoucherRepository.save(fixedAmountVoucher);
//
//        // then
//        Voucher savedVoucher = jdbcVoucherRepository.findAll().get(0);
//
//        assertThat(fixedAmountVoucher)
//                .usingRecursiveComparison()
//                .isEqualTo(savedVoucher);
//    }
//
//    @Test
//    @DisplayName("바우처 ID로 찾기 성공")
//    void findVoucherById() {
//        // given
//        Voucher voucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, "customerId");
//        Voucher anotherVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, "customerId");
//
//        jdbcVoucherRepository.save(voucher);
//
//        // when
//        Voucher findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId()).get();
//
//        // then
//        assertThat(voucher).usingRecursiveComparison()
//                .isEqualTo(findVoucher);
//    }
//
//    @Test
//    @DisplayName("찾는 바우처 ID가 DB에 없는 경우")
//    void findVoucherIdNotExist() {
//
//        // given
//        Voucher voucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, "customerId");
//        Voucher anotherVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, "customerId");
//
//        jdbcVoucherRepository.save(voucher);
//
//        // when
//        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(anotherVoucher.getVoucherId());
//
//        // then
//        assertThat(findVoucher).isEmpty();
//    }
//
//    @Test
//    @DisplayName("모든 바우처 조회 성공")
//    void findAllVouchers() {
//        // given
//        Voucher voucher1 = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, "customerId1");
//        Voucher voucher2 = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, "customerId2");
//
//        jdbcVoucherRepository.save(voucher1);
//        jdbcVoucherRepository.save(voucher2);
//
//        // when
//        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();
//
//        // then
//        assertThat(allVouchers).hasSize(2);
//        assertThat(allVouchers)
//                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
//                .contains(tuple(voucher1.getVoucherId(), voucher1.getDiscountAmount(), voucher1.getVoucherType(), voucher1.getCustomerId()))
//                .contains(tuple(voucher2.getVoucherId(), voucher2.getDiscountAmount(), voucher2.getVoucherType(), voucher2.getCustomerId()));
//    }
//
//    @Test
//    @DisplayName("DB에 데이터가 존재하지 않는 경우 모든 바우처 조회 성공")
//    void findAllVouchersNotExist() {
//
//        // when
//        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();
//
//        // then
//        assertThat(allVouchers).isEmpty();
//    }
//
//    @Test
//    @DisplayName("DB 데이터 전부 삭제")
//    void deleteAll() {
//        // given
//        Voucher voucher1 = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, "customerId1");
//        Voucher voucher2 = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, "customerId2");
//
//        jdbcVoucherRepository.save(voucher1);
//        jdbcVoucherRepository.save(voucher2);
//
//        // when
//        jdbcVoucherRepository.clear();
//
//        // then
//        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();
//        assertThat(allVouchers).isEmpty();
//    }
//
//    private Voucher createVoucher(UUID uuid, VoucherType voucherType, int discountAmount, String customerId) {
//        return VoucherType.createVoucher(VoucherCreateDTO.of(uuid, voucherType.name(), discountAmount, customerId));
//    }
//
//    @Configuration
//    @ComponentScan(
//            basePackages = {"org.prgrms.vouchermanagement.customer"}
//    )
//    static class Config {
//
//        @Bean
//        public DataSource dataSource() {
//            return DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost:2215/test-customer")
//                    .username("test")
//                    .password("test")
//                    .type(HikariDataSource.class)
//                    .build();
//        }
//
//        @Bean
//        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//            return new JdbcTemplate(dataSource);
//        }
//
//        @Bean
//        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
//            return new NamedParameterJdbcTemplate(dataSource);
//        }
//
//        @Bean
//        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
//        }
//    }

}