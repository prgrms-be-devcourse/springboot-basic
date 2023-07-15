package com.programmers.springmission.view.output;

import java.util.List;

public interface Output {

    void write(String message);

    void write(List<?> responses);
}
