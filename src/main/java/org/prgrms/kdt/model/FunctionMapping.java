package org.prgrms.kdt.model;

import java.util.HashMap;
import java.util.Map;

public class FunctionMapping {
    private static Map<String, Function> functionMap = new HashMap<>();

    static {
        functionMap.put("create", Function.create);
        functionMap.put("list", Function.list);
        functionMap.put("exit", Function.exit);
    }

    public static Function getFunction(String function) {
        return functionMap.get(function);
    }
}
