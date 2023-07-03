package com.ray.junho.voucher.view;

import java.util.List;

public interface Console {
    String read();

    void print(String message);

    void print(List<String> message);
}
