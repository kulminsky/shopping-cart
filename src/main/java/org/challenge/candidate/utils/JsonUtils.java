package org.challenge.candidate.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static String convertToJson(String key, String value) {
        var mapper = new ObjectMapper();
        var jsonNode = mapper.createObjectNode();
        jsonNode.put(key, value);
        return jsonNode.toString();
    }
}
