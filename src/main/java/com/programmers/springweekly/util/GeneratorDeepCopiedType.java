package com.programmers.springweekly.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GeneratorDeepCopiedType <K, V>{

    public Map<K, V> copiedMap(Map<K, V> map){
        Map<K, V> copiedMap = new ConcurrentHashMap<>();

        for(Map.Entry<K, V> entry : map.entrySet()){
            copiedMap.put(entry.getKey(), entry.getValue());
        }

        return copiedMap;
    }
}
