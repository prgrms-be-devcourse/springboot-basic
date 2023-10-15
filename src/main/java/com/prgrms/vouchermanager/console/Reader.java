package com.prgrms.vouchermanager.console;

import com.prgrms.vouchermanager.exception.FileIOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class Reader {

    Scanner sc = new Scanner(System.in);

//    @Value("${csv.path}")
//    private String path;

    File file = new File("src\\main\\resources\\customer_blacklist.csv");

    public BufferedReader bf = new BufferedReader(new FileReader(file));

    public Reader() throws IOException {
    }
}
