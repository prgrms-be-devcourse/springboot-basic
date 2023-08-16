package org.prgrms.kdt.model.repository.jdbc;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {
	@Configuration
	@ComponentScan(
		basePackages = {"org.prgrms.kdt.model.repository.jdbc"}
	)
	static class Config {
		@Bean
		public DataSource dataSource() {
			HikariDataSource dataSource = DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:2215/test-order-mgmt")
				.username("test")
				.password("test1234!")
				.type(HikariDataSource.class)
				.build();

			dataSource.setMinimumIdle(10);
			dataSource.setMaximumPoolSize(100);
			return dataSource;
		}

		@Bean
		public JdbcTemplate jdbcTemplate(DataSource dataSource) {
			return new JdbcTemplate(dataSource);
		}

		@Bean
		public IdGenerator idGenerator() {
			return new IdGenerator();
		}
	}

	@Autowired
	VoucherJdbcRepository voucherJdbcRepository;

	@Autowired
	IdGenerator idGenerator;

	private VoucherEntity newVoucher;

	EmbeddedMysql embeddedMysql;

	@BeforeAll
	void setup() {
		newVoucher = new VoucherEntity(idGenerator.getRandomId(), 100, "FixedAmountVoucher");
		var mysqlConfig = aMysqldConfig(v5_7_27)
			.withCharset(Charset.aCharset("utf8mb4", "utf8mb4_bin"))
			.withPort(2215)
			.withUser("test", "test1234!")
			.withTimeZone("Asian/Seoul")
			.build();

		embeddedMysql = anEmbeddedMysql(mysqlConfig)
			.addSchema("test-order-mgmt", classPathScript("voucherSchema.sql"))
			.start();
	}

	@AfterAll
	void cleanup() {
		embeddedMysql.stop();
	}

	@Test
	@Order(1)
	@DisplayName("바우처를 추가할 수 있다.")
	public void 바우처_생성_테스트() {
		voucherJdbcRepository.saveVoucher(newVoucher);

		var vouchers = voucherJdbcRepository.findAllEntities();

		assertThat(vouchers.isEmpty(), is(false));
		assertThat(vouchers.get(0), samePropertyValuesAs(newVoucher));
	}

	@Test
	@Order(2)
	@DisplayName("전체 바우처를 조회할 수 있다.")
	public void 전체_바우처_조회_테스트() {
		//when
		Optional<VoucherEntity> voucher = voucherJdbcRepository.findVoucherById(newVoucher.getVoucherId());

		//then
		assertThat(voucher.isEmpty(), is(false));
		assertThat(voucher, samePropertyValuesAs(newVoucher));
	}

	@Test
	@Order(3)
	@DisplayName("바우처를 수정할 수 있다.")
	public void 바우처_수정_테스트() {
		VoucherEntity updatedVoucher = new VoucherEntity(newVoucher.getVoucherId(), newVoucher.getAmount(),
			newVoucher.getVoucherType());

		voucherJdbcRepository.updateVoucher(updatedVoucher);

		List<VoucherEntity> all = voucherJdbcRepository.findAllEntities();
		assertThat(all, hasSize(1));
		assertThat(all, everyItem(samePropertyValuesAs(updatedVoucher)));
	}

	@Test
	@Order(4)
	@DisplayName("바우처를 삭제할 수 있다.")
	public void 바우처_삭제_테스트() {
		Long voucherId = newVoucher.getVoucherId();

		voucherJdbcRepository.deleteVoucherById(voucherId);

		List<VoucherEntity> all = voucherJdbcRepository.findAllEntities();
		assertThat(all, hasSize(0));
	}
}