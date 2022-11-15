package org.prgrms.springorder.jdbctest;

import org.prgrms.springorder.config.JdbcProperties;
import org.prgrms.springorder.global.CommandLineController;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;


@JdbcTest
@Import(value = {JdbcProperties.class})
@Sql(scripts = {"classpath:/schema.sql"})
public class JdbcTestBase {

    @MockBean
    private CommandLineController controller;

}
