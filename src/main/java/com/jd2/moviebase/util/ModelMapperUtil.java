package com.jd2.moviebase.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T, U> U mapObject(T source, Class<U> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }
}
