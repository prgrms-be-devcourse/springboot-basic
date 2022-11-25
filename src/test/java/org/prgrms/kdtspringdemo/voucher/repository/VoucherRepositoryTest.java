package org.prgrms.kdtspringdemo.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherCreator;
import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.model.VoucherType;
import org.prgrms.kdtspringdemo.domain.voucher.repository.FileVoucherRepository;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherNamedJdbcRepository;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringJUnitConfig
@DisplayName("VoucherRepository 에서")
class VoucherRepositoryTest {
    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8").withInitScript("schema.sql");
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    DataSource dataSource;
    @Autowired
    VoucherCreator voucherCreator;

    @Configuration
    @ComponentScan(basePackages = "org.prgrms.kdtspringdemo.domain.voucher.repository",
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = FileVoucherRepository.class))
    static class Config {
        @Bean
//        @ConfigurationProperties(prefix = "")
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mySQLContainer.getJdbcUrl())
                    .username(mySQLContainer.getUsername())
                    .password(mySQLContainer.getPassword())
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public VoucherCreator voucherCreator() {
            return new VoucherCreator();
        }

        @Bean
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, VoucherCreator voucherCreator) {
            return new VoucherNamedJdbcRepository(namedParameterJdbcTemplate, voucherCreator);
//            return new MemoryVoucherRepository();
        }
    }

    @Nested
    @DisplayName("Voucher를 insert할때")
    class testInsert {

        @AfterEach
        void cleanup() {
            voucherRepository.deleteAll();
        }

        @Test
        @DisplayName("insert 성공합니다.[정상 케이스]")
        void success() {
            //given
            var fixedVoucher = voucherCreator.createVoucher(VoucherType.FIXED, 10000L);
            //when
            var insertedVoucher = voucherRepository.insert(fixedVoucher).get();
            assertThat(insertedVoucher).isEqualTo(fixedVoucher);
            //then
        }

        @Test
        @DisplayName("실패합니다.[중복된 ID]")
        void failEqualId() {
            var voucher1 = voucherCreator.createVoucher(VoucherType.FIXED, 10000L);
            var voucher2 = voucherCreator.createVoucher(voucher1.getVoucherId(), VoucherType.PERCENT, 30L, voucher1.getCreatedAt());
            voucherRepository.insert(voucher1);
            assertThat(voucherRepository.insert(voucher2).isPresent()).isFalse();
        }
    }

    @Nested
    @DisplayName("Voucher를 find 할때")
    class testFind {
        private final List<UUID> ids = new ArrayList<>();

        @BeforeEach
        void setup() {
            for (int i = 0; i < 4; i++) {
                var voucher = voucherCreator.createVoucher(VoucherType.PERCENT, i * 10L);
                var insertedVoucher = voucherRepository.insert(voucher);
                if (insertedVoucher.isPresent()) {
                    ids.add(insertedVoucher.get().getVoucherId());
                }
            }
        }

        @AfterEach
        void cleanUp() {
            voucherRepository.deleteAll();
        }

        @Test
        @DisplayName("id로 찾는데 성공합니다.[정상케이스]")
        void success() {
            //given
            var fixedVoucher = voucherCreator.createVoucher(VoucherType.FIXED, 10000L);
            voucherRepository.insert(fixedVoucher);
            //when
            var voucher = voucherRepository.findById(fixedVoucher.getVoucherId());
            //then
            assertThat(voucher.get()).isEqualTo(fixedVoucher);

        }

        @Test
        @DisplayName("id로 찾는데 실패합니다.[없는 id]")
        void failNotFound() {
            var voucher = voucherRepository.findById(UUID.randomUUID());
            assertThat(voucher.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("전체 찾기에 성공합니다.[정상 케이스]")
        void successFindAll() {
            //given
            //when
            var allVaucher = voucherRepository.findAllVaucher();
            //then
            assertThat(allVaucher.isEmpty()).isFalse();
            allVaucher.sort(Comparator.comparing(Voucher::getVoucherId));
            ids.sort(Comparator.naturalOrder());
            for (int i = 0; i < ids.size(); i++) {
                assertThat(allVaucher.get(i).getVoucherId()).isEqualTo(ids.get(i));
            }
        }
    }

//    @Nested
//    @DisplayName("Voucher를 update할 때")
//    class testUpdate {
//        @Test
//        @DisplayName("성공합니다.")
//        void success() {
//        }
//
//        @Test
//        @DisplayName("실패합니다.찾을 수 없는 id.")
//        void failNotFound() {
//        }
//
//        @Test
//        @DisplayName("실패합니다.바꿀수 없는 필드의 변경-email")
//        void failChangeEmail() {
//        }
//    }

    @Nested
    @DisplayName("Voucher를 delete 할때")
    class testDelete {
        @Test
        @DisplayName("성공합니다.")
        void success() {
            //given
            var voucher = voucherCreator.createVoucher(VoucherType.PERCENT, 10L);
            voucherRepository.insert(voucher);
            //when
            voucherRepository.deleteById(voucher.getVoucherId());
            //then
            assertThat(voucherRepository.findAllVaucher().isEmpty()).isTrue();
        }

        @Test
        @DisplayName("실패합니다.찾을 수 없는 id")
        void failNotFound() {
            //given
            var voucher = voucherCreator.createVoucher(VoucherType.PERCENT, 10L);
            voucherRepository.insert(voucher);
            //when
            voucherRepository.deleteById(UUID.randomUUID());
            //then
            assertThat(voucherRepository.findAllVaucher().isEmpty()).isFalse();
        }
    }


}