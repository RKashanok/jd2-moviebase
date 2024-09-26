package com.jd2.moviebase.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T, U> U mapObject(T source, Class<U> destinationClass) {
        return objectMapper.convertValue(source, destinationClass);
    }
}
