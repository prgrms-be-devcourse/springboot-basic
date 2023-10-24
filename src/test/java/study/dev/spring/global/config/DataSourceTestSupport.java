package study.dev.spring.global.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@Transactional
public abstract class DataSourceTestSupport {

	private static final String DDL = """
		CREATE TABLE Voucher(
		    uuid           VARCHAR(36)  NOT NULL,
		    name           VARCHAR(255) NOT NULL UNIQUE,
		    voucherType           VARCHAR(255) NOT NULL,
		    discountAmount DOUBLE       NOT NULL
		);
		""";

	@TestConfiguration
	static class DataSourceConfig {

		@Bean
		public DataSource dataSource() {
			DataSource dataSource = DataSourceBuilder.create()
				.url("jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1")
				.driverClassName("org.h2.Driver")
				.username("sa")
				.password("")
				.type(HikariDataSource.class)
				.build();

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.execute(DDL);

			return dataSource;
		}

		@Bean
		public PlatformTransactionManager transactionManager(DataSource dataSource) {
			return new DataSourceTransactionManager(dataSource);
		}
	}
}
