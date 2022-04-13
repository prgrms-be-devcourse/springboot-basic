package org.programmers.springbootbasic.console;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Model {
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final RedirectData redirectData = new RedirectData();
    @Getter
    private String inputSignature;
    @Getter
    private String previousInputLink;

    public void addAttributes(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    public Object getAttributes(String name) {
        return attributes.get(name);
    }

    public boolean hasRedirectLink() {
        return this.redirectData.isHavingRedirectLink();
    }

    public String getRedirectLink() {
        return this.redirectData.getRedirectLink();
    }

    public void clear() {
        attributes.clear();
    }

    public void setNoRedirectLink() {
        this.redirectData.setHavingRedirectLink(false);
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectData.setRedirectLink(redirectLink);
        this.redirectData.setHavingRedirectLink(true);
    }

    public void setInputEnvironment(String inputSignature, String previousInputLink) {
        this.inputSignature = inputSignature;
        this.previousInputLink = previousInputLink;
    }

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    static class RedirectData {
        boolean havingRedirectLink = false;
        String redirectLink = "";
    }
}
