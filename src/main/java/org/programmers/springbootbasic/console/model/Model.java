package org.programmers.springbootbasic.console.model;

import lombok.Getter;
import lombok.Setter;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Model {
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();
    @Getter @Setter
    private String inputSignature;
    @Getter @Setter
    private RedirectCommand redirectLink;

    public void addAttributes(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    public Object getAttributes(String name) {
        return attributes.get(name);
    }

    public void clear() {
        attributes.clear();
    }
}
