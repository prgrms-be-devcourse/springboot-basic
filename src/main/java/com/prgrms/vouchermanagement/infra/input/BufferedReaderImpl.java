package com.prgrms.vouchermanagement.infra.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

@Component
public class BufferedReaderImpl implements InputProvider {

    private static final Pattern MENU = Pattern.compile("^(list|create|exit)$");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String getMenuType() throws IOException {
        return bufferedReader.readLine();
    }
}
