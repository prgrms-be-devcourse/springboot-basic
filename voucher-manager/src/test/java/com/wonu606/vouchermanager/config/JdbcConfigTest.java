package com.wonu606.vouchermanager.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class JdbcConfigTest {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    @MockBean
    private CommandLineRunner commandLineRunner;

    @Autowired
    public JdbcConfigTest(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    public void setup() throws Exception {
        doNothing().when(commandLineRunner).run(any());
    }

    @Test
    public void DI_CreatedDataSource_NotNull() {
        assertNotNull(dataSource);
    }

    @Test
    public void DI_CreatedJdbcTemplate_NotNull() {
        assertNotNull(jdbcTemplate);
    }
}
