package org.programmers.springbootbasic.console;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("console")
@RequiredArgsConstructor
@Getter
@Setter(AccessLevel.PACKAGE)
public class ConsoleProperties {

    @Value("${console.error.detail}")
    private boolean detailErrorMessage;
}