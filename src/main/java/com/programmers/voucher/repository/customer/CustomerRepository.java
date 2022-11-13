package com.programmers.voucher.repository.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {

    private final String fileName;

    public CustomerRepository(@Value("${file.path.black}") String fileName) {
        this.fileName = fileName;
    }

    public List<String> findAllBlack() throws IOException{
        BufferedReader buffer = new BufferedReader(new FileReader(fileName));
        return buffer.lines()
                .collect(Collectors.toList());
    }
}
