package com.example.demo.customer;

import com.example.demo.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository{
    public Optional<ArrayList<Customer>> getAll() throws IOException;
}
