package com.programmers.springmission.view;

import java.util.List;

public interface Output {
    void write(String message);

    void write(List<?> responses);
}

