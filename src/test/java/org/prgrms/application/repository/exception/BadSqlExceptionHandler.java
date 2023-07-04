package org.prgrms.application.repository.exception;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.springframework.jdbc.BadSqlGrammarException;

import static org.assertj.core.api.Fail.fail;

public class BadSqlExceptionHandler implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (throwable instanceof BadSqlGrammarException){
            fail("Unexpected BadSqlGrammarException occurred");
        }else {
            throw throwable;
        }
    }
}
