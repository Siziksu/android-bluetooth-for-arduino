package com.siziksu.bluetooth.common.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public static Map<String, String> linkedMapZip(List<String> keys, List<String> values) {
        if (keys.size() != values.size()) { throw new IllegalArgumentException("Cannot combine lists with different sizes"); }
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }
}
