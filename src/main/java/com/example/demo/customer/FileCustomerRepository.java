package com.example.demo.customer;

import com.example.demo.exception.WrongCustomerParamsException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static java.util.stream.Collectors.toCollection;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    File file;

    public FileCustomerRepository(File file) {
        this.file = file;
    }

    @Override
    public Optional<ArrayList<Optional<Customer>>> getAll() throws IOException {
        //파일 한 줄마다 고객 정보를 인스턴스로 저장한 다음 ArrayList로 저장한다.
        return Optional.ofNullable(
                Files.readAllLines(file.toPath())
                        .stream()
                        .map((e)-> {
                            try {
                                return Optional.ofNullable(new Customer(new ArrayList<String>(Arrays.asList(e.split(",")))));
                            } catch (WrongCustomerParamsException ex) {
                            return Customer.EMPTY;
                            }
                        })
                        .filter((e) -> {
                            return !e.isEmpty();
                        })
                        .collect(toCollection(ArrayList::new))
        );
    }
}
