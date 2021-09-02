package org.prgrms.kdt.kdtspringorder.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class MapConverter {

    public static Map<String, Object> toParamMap(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(obj, HashMap.class);
    }

}
