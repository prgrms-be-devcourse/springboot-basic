package org.prgrms.springorder.common;

import org.prgrms.springorder.CommandLineApplication;
import org.prgrms.springorder.config.JdbcProperties;
import org.prgrms.springorder.console.core.CommandLineDispatcher;
import org.prgrms.springorder.console.io.Console;
import org.prgrms.springorder.console.io.ConsoleRequestBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;


@JdbcTest
@Import(value = {JdbcProperties.class})
@Sql(scripts = {"classpath:/schema.sql"})
public class JdbcTestBase {

    @MockBean
    private CommandLineDispatcher controller;

    @MockBean
    private ConsoleRequestBuilder consoleRequestBuilder;

    @MockBean
    private Console console;

    @MockBean
    private CommandLineApplication commandLineApplication;

}
