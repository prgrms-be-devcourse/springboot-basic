package study.dev.spring.global.support;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@Transactional
@TestPropertySource("classpath:application.properties")
public abstract class DataSourceTestSupport {

	@TestConfiguration
	static class DataSourceConfig {

		@Value("${spring.datasource.url}")
		private String url;
		@Value("${spring.datasource.driver-class-name}")
		private String driverClasName;
		@Value("${spring.datasource.username}")
		private String username;
		@Value("${spring.datasource.password}")
		private String password;

		@Bean
		public DataSource dataSource() {
			DataSource dataSource = DataSourceBuilder.create()
				.url(url)
				.driverClassName(driverClasName)
				.username(username)
				.password(password)
				.type(HikariDataSource.class)
				.build();

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.execute(SqlFixture.DDL);

			return dataSource;
		}

		@Bean
		public PlatformTransactionManager transactionManager(DataSource dataSource) {
			return new DataSourceTransactionManager(dataSource);
		}
	}
}
