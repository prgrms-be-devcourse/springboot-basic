package org.programmers.springbootbasic.console;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Model {
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    void addAttributes(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    Object getAttributes(String name) {
        return attributes.get(name);
    }

    void clear() {
        attributes.clear();
    }
}
